package com.aktasbdr.cryptotracker.core.data.network

import com.aktasbdr.cryptotracker.BuildConfig

//Ktor'da "baseUrl" olmadigi icin bu fonksiyonu olusturduk
fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        // eger "/assets" gelirse Base'in arkasina ekle ve "/" sil
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}