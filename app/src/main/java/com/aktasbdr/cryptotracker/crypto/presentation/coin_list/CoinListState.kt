package com.aktasbdr.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.aktasbdr.cryptotracker.crypto.presentation.model.CoinUi

//Immutable ile Compose Compile'a bu class immutable yani hicbir sekilde degismez diyoruz
// eger degisirse de butun instancelar yenisiyle degisecek
// burda List kullandigimiz icin Compose her seferinde recompile yapmak isteyebilir
// cunku List Compose tarafindan stable olarak tanimlanmaz. Compose liste degistikce bunu anlayip recompile yapmak istiyecek
// biz de Immutable ile -> bunu yapmana gerek yok liste degistikce butun CoinListState degisecek diyoruz
// tabi alternative olarak Kotlinx.collections.immutable kullanabiliriz bunu Stable olarak tanimlar
// ama bunu kullanmak icin de additional dependency kullanmak gerek o yuzden bu yontem secildi
@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    //API'dan aldigimiz Coin listesi
    val coins: List<CoinUi> = emptyList(),
    //bunu daha sonra Update icin kullanicaz
    val selectedCoin: CoinUi? = null
)