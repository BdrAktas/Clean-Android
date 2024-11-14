package com.aktasbdr.cryptotracker.di


import com.aktasbdr.cryptotracker.core.data.network.HttpClientFactory
import com.aktasbdr.cryptotracker.crypto.domain.CoinDataSource
import com.aktasbdr.cryptotracker.crypto.data.network.RemoteCoinDataSource
import com.aktasbdr.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

// app lifetime'a bound oldugu icin buna appModule diyoruz
val appModule = module {
    // single ile singleton yapiyoruz
    // burda CIO engine pass ediyoruz ama test icin MockEngine pass edilecek
    single { HttpClientFactory.create(CIO.create()) }
//    single { RemoteCoinDataSource(get()) } boyle de yapabilirdik
//    Koin get ile RemoteCoinDataSource constructorina bakip HttpClientFactory dependecysini otomatik bulabilirdi
// VM'umuz aslinda CoinDataSource abstractionini  kullaniyor direk concrete implemantasyonu yani RemoteCoinDataSource kullanmiyor
// biz de Koin'e diyoruz ki ne zaman abstractiona(CoinDataSource) request yapilirsa, asil implementasyon olan RemoteCoinDataSource'u inject et
    // artik herhangi bir classta  CoinDataSource instanceini request edersek RemoteCoinDataSource implementasyonunu kullan diyoruz
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinListViewModel)
}