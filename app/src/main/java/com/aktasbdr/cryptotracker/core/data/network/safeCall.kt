package com.aktasbdr.cryptotracker.core.data.network

import com.aktasbdr.cryptotracker.core.domain.util.NetworkError
import com.aktasbdr.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

// yapacagimiz requestleri safeCall ile handle edebilicez
suspend inline fun <reified T> safeCall(
    // execute -> HttpResponse(yapmak istedigimiz request) return eder
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        // en basta HttpResponse yapicak sonra catch
        execute()
    }
    //Burdaki cartchler responseToResult ile farki
    // daha serverdan response almadan catch edebilecegimiz hatalari da catch edebilir
    catch(e: UnresolvedAddressException) {
        // Clent is unable to resolve  host name, genelde internet olmadigi icin firlatilir
        return Result.Error(NetworkError.NO_INTERNET)
    } catch(e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch(e: Exception) {
        // Diger turlu Generic Error firlat
        // Ama burda eger suspend fonksiyonu icinde Generic Error catch yapinca hata alacak
        // Cunku Coroutine cancel olunca Cancellation Exception throw edicek ve Parent notify olmucak bu durumdan
        // Cozum olarak asagida satirla eger Cacellation Exception throw edilirse tekrar edip Parent notify edilecek
        //TODO : https://www.youtube.com/watch?v=VWlwkqmTLHc&t=1065s Coroutine Cancellation & Exception Handling
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    // hata olsa bile responseToResult ile dogru yerine map edicez
    return responseToResult(response)
}