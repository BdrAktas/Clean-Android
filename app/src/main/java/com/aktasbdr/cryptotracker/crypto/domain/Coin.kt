package com.aktasbdr.cryptotracker.crypto.domain


//bunu bitirdikten sonra app artik Coin ne demek biliyor olacak
data class Coin (
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    // burda best practice olarak kullandigin unitle append etmek yararli
    // cunku kullandigin unit'in diger developerlar da bilmesi lazim(km,m,mile,usd,tl ...)
    val priceUsd: Double,
    // Percentage deger 0-1 arasinda
    val changePercent24Hr: Double,
    val marketCapUsd: Double
)

