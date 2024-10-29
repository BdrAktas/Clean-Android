package com.aktasbdr.cryptotracker.core.data.network

import com.aktasbdr.cryptotracker.core.domain.util.NetworkError
import com.aktasbdr.cryptotracker.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

// Ktor'da HttpResponse'dan gelen response'u bu fonksiyon ile Result ceviricez
// suspend -> cunku response.body<T> suspend fonksiyonudur
// inline -> cunku reified icin -> Inline kullanildiginda Kotlin compiler to copy the function’s body to the call site, instead of making a traditional function call.
// reified -> response.body fonsiyonun T data typeni tanimasi icin
    // cunku runtime'de T data type'ini alabiliyoruz diger turlu generic type information is erased at runtime bi tek compile time'da alinir
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
   // Onemli: Iste burda Result'un faydasini goruyoruz
    // Data Layer'dan Result' sayesinde <reified T> ile single return type'i alabiliyoruz
    // sonrasinda Presentationda unwrap edip Success data ve Error message olarak kullanabiliyoruz
): Result<T, NetworkError> {
    return when(response.status.value) {
        // Gelen response JSON body'deki JSON'i T data typesini dondurur(burda T-> list of coin)
        // convert ederken fail olursa SerializationException firlatir
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }

        // Client errors (400-499)
        400 -> Result.Error(NetworkError.BAD_REQUEST) // Bad request
        401 -> Result.Error(NetworkError.UNAUTHORIZED) // Unauthorized access
        403 -> Result.Error(NetworkError.FORBIDDEN) // Forbidden access
        404 -> Result.Error(NetworkError.NOT_FOUND) // Resource not found
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT) // Request timeout
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS) // Too many requests
        in 400..499 -> Result.Error(NetworkError.CLIENT_ERROR) // Other client errors

        // Server errors (500-599)
        500 -> Result.Error(NetworkError.INTERNAL_SERVER_ERROR) // Internal server error
        502 -> Result.Error(NetworkError.BAD_GATEWAY) // Bad gateway
        503 -> Result.Error(NetworkError.SERVICE_UNAVAILABLE) // Service unavailable
        504 -> Result.Error(NetworkError.GATEWAY_TIMEOUT) // Gateway timeout
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR) // Other server errors

        else -> Result.Error(NetworkError.UNKNOWN)
    }
}