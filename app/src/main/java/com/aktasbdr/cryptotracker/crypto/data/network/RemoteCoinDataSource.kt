package com.aktasbdr.cryptotracker.crypto.data.network

import com.aktasbdr.cryptotracker.core.data.network.constructUrl
import com.aktasbdr.cryptotracker.core.data.network.safeCall
import com.aktasbdr.cryptotracker.core.domain.util.NetworkError
import com.aktasbdr.cryptotracker.core.domain.util.Result
import com.aktasbdr.cryptotracker.core.domain.util.map
import com.aktasbdr.cryptotracker.crypto.data.network.dto.CoinsResponseDto
import com.aktasbdr.cryptotracker.crypto.data.mappers.toCoin
import com.aktasbdr.cryptotracker.crypto.domain.Coin
import com.aktasbdr.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get


// Burda normalde repository pattern ile cagrildiginda repositroy altinda CoinRepositoryIml yapmamiz lazimdi
// ama tek data source o yuzden CoinDataSource implementationi burda yapacagiz

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        //CoinsResponseDto: expected data class which JSON data will be parsed into
        return safeCall<CoinsResponseDto> {
            //relative url "/assets" ile get request yapilacak
            httpClient.get(
                urlString = constructUrl("/assets")
            )
            //gelen response data listesin olucak
        }.map { response ->
            // burda direk CoinDto modeli kullanamiyoruz onun yerine Coin modelini kullanacagiz
            // yoksa Domain layer buna bagli olucak o yuzde mappers kullanicagiz
            // it -> herbir eleman ->  CoinDto
            //burdaki map fonksiyonu yukardaki Result dosyasindaki map fonksiyonunundan farkli olarak
            // Kotlin'in extension functioni olan map fonksiyonudur
            response.data.map { it.toCoin() }
        }
    }
}