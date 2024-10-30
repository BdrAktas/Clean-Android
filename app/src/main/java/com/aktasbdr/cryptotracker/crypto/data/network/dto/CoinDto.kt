package com.aktasbdr.cryptotracker.crypto.data.network.dto

import kotlinx.serialization.Serializable

//Dto: Data Transfer Object -> yani remote dan gelen JSON verilerinin Kotlin data classina parse edilmesi icin
// Bu donusturmeyi otamatik olarak kotlinx.serialization yapicak

// peki neden Json responseindaki data typelariyla tutarli olmasina ragmen neden @Serializable ile
// Domain katmandaki Coin Data classina parse etmedik
    // cunku violation of architecture olur->
        // 1- her zaman JSON'daki data ayni olmayabilir
            // mesela JSON'dan genelde time verisi String ya da Long olarak gelir bunlarla kodun icinde ugrasmak zordur
                // biz genelde data layerindekini oldugu gibi alip Domaindekinde kolay handle edecegimiz ZonedDateTime, LocalDate kullanirz
        // 2- domain layerinde 3 party library olamaz Serializable,Moshi,Gson gibi cunku direk domaini impl detaile couple etmis oluruz
            // cunku how we parse JSON implementation detaildir
@Serializable
data class CoinDto(
    //burda onemli olan responsedaki JSON'nin variable isimlerini exactly almaliyiz
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
    // volumeUsd, supply, maxSupply Gelen response JSON'da olsa da biz kullanmicagimiz icin tanimlamadik
    // bunlar ignore edilecek ignoreUnknownKeys = true sayesinde
)