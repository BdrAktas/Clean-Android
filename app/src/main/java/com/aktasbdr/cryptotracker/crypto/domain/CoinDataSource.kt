package com.aktasbdr.cryptotracker.crypto.domain

import com.aktasbdr.cryptotracker.core.domain.util.NetworkError
import com.aktasbdr.cryptotracker.core.domain.util.Result


// Burda normalde repository pattern ile cagrilmasi lazim ama bizim bir tane data sourcemuz var o yuzden direk boyle cagirdik
// burda onemli olan burda su sorularan cevap veriyoruz
// what is Data Source consist of, what kind of data we expected(ki burda Result<List<Coin>, NetworkError>)
// eger how retrive data kismina dusunursek bu imp detaildir ve bu data layerle ilgili olur
// domain katmaninda most inner layerdir -> Presentation ve Data Layer Domainin classlarina ulasabilir ama tam tersi olamaz

interface CoinDataSource {
    //expected data type Result<List<Coin>, excepted error type NetworkError>
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}