package com.aktasbdr.cryptotracker.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            //logging feature log icin install edildi
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            // ContentNegotiation feature: Ktor'un otomatik olarak JSON responselari  Kotlin data classlarimiza parse edebilmesi icin
            //
            install(ContentNegotiation) {
                // json functioni ile bu parse islemini kotlinx.serialization ile yap diyoruz
                json(
                    json = Json {
                        //eger response'ta Data Classimizda olmayan unknown key varsa crash olmamasi icin ignore edilecek
                        ignoreUnknownKeys = true
                    }
                )
            }
            // yaptigimiz her request JSON formatinda olacak, asagida satir ile proper header olusturulucak
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}