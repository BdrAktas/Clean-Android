package com.aktasbdr.cryptotracker.crypto.presentation.coin_list

import com.aktasbdr.cryptotracker.crypto.presentation.model.CoinUi

//limited uyesi oldugu icin sealed class
sealed interface CoinListAction {
    // sonradan OnCoinClick icin detail screen gidecegimiz icin CoinUi parametresini veriyoruz
    //Argument, fonksiyon çağrıldığında parametrelere atanan gerçek değerlerdir.
    // mesela cagirdigimiz yerde OnCoinClick(coinUi = myCoinUi) diyince myCoinUi degeri argument olurdu
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
    //parametre almiyacagi icin data object
    data object OnRefresh: CoinListAction
}