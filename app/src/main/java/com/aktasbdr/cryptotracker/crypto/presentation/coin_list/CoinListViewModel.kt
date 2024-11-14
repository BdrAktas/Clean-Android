package com.aktasbdr.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktasbdr.cryptotracker.core.domain.util.onError
import com.aktasbdr.cryptotracker.core.domain.util.onSuccess
import com.aktasbdr.cryptotracker.crypto.domain.CoinDataSource
import com.aktasbdr.cryptotracker.crypto.presentation.model.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CoinListViewModel(
    // burda inject edecegimiz data source Domain katmanindaki interfaceyi kullanacagiz
    // eger RemoteCoinDataSource kullansaydik -> violence of architecture yapardik
    // boylece istedigimiz kadar farkli coinDataSource instacelarini VM'dan gecirebilicegiz
    // mesela simdi VM'i test etmek istegimizde farkli bir instance gecirebilecegiz
     private val coinDataSource: CoinDataSource
): ViewModel() {

    // Burda MVI kullanacagizmiz icin VM'da tek global state kullanacagiz, yani state'in tek instance'i  olacak
    // burda genel tartisma StateFlow mu kullanacagiz yoksa Compose State mi kullanacagiz?
    // Aslinda aralarinda buyuk fark yok ama biz burda StateFlow kullanacagiz
    // cunku istedigimiz zaman state'i Flow operators ile trasform edebilme ozgurlumuz var, Compose'da bu operatorlar yok
    // burda _state'i private yaptik cunku sadece VM bu mutable degeri update edebilir
    // initial deger CoinListState() olucak
    private val _state = MutableStateFlow(CoinListState())
    // burdaki state _state'in global ve  immutable hali
    // UI sadece bunu okuyacak degistiremicek


//    val state = _state.asStateFlow()
//    init {
//        loadCoins()
//    }
    // burada populer bir tartisma var -> Screen gorulunce initial datayi nasil gosterebiliriz
    //ya da yukaridaki gibi init{} icinde calistirabiliriz ya da asagidaki gibi flow collection start ettiginde calistirabiliriz
    // init{} ile side effect ile geliyor ve VM'in creationina couple oluyor ve burdaki functioni calistirma kontrolu bizde olmuyor
    //  bu yuzden Test Case yazmak daha zor oluyor
    // Daha fazla bilgi icin: https://www.youtube.com/watch?v=mNKQ9dc1knI
    val state = _state
        // onStart -> flow collection start oldugunda call yapilir
        // burdaki casede when ComposeUI start subscribing to update of the state
        // boylece istegimiz zaman subscribe olup loadCoins() calistirabilecegiz ve bu test caselerde kullanabilecegiz
        .onStart { loadCoins() }
        // yukardaki sekliyle normal Flow donuyor biz bunu stateIn() ile StateFlow'a ceviriyoruz cunku resultlari cache yapmak istiyoruz
        .stateIn(
            viewModelScope,
            // asagidaki sekilde -> keep executing flow as long as there is no more subscriber + 5 saniye
            // yani last subscriber'dan sonra(yani artik bu flowu dinleyen yok) yeni bir subscriber gelmiyosa 5 saniye bekle sonra
            // flow chaini execute etmeyi stop et
            // bu da isimize configuration changelerde isimize yarar
            // diyelim rotate oldu activity destroy oldu, flowa yeni bir subscriber/collector geldi
            // ama bu collector bu 5 saniye icinde appear oldugu icin loadCoins()'i refetch etmiyoruz
            SharingStarted.WhileSubscribed(5000L),
            //yine initialValue olarak CoinListState() veriyoruz
            CoinListState()
        )

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {

            }
            is CoinListAction.OnRefresh -> {
                loadCoins()
            }
        }
    }

    private fun loadCoins() {
        //coinDataSource.getCoins() suspend oldugu icin coroutineScope icinde calistiracagiz
        viewModelScope.launch {
            //update functioni thread safe bir sekilde value update edebilir, yani race condition olamaz
            _state.update {
                // burda it existing _state'i temsil ediyor
                // bunun sadece isLoading'i update ediyoruz
                it.copy(
                    isLoading = true
                )
            }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            //burda  CoinListState icindeki coins oldugu icin List<CoinUi> bekliyor
                            // bizim elimizde Domain layerindaki getCoins()dan dolayi List<Coin> var
                            // bunlarin herbirini dogru formatlanmis degerleriyle presentationin kullanacagi CoinUi modeline ceviriyoruz
                            coins = coins.map { it.toCoinUi() }
                        )
                    }
                }
                .onError { error ->
                    // burda error'i handle'i sonra yapacagiz simdilik sadece isLoading'i false yapacagiz
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}