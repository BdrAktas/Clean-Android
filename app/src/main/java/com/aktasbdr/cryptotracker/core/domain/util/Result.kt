package com.aktasbdr.cryptotracker.core.domain.util

typealias DomainError = Error

// Burdaki sealed Class kullanimi
// Mesela Result ->Success ise Coin listesini donicek olacak
//               ->Error ise Data layerden bu bilgiyi alicak(internet yok, server hata...) bunu Domain ustunden Presentation Layerine aktaricagiz

// Result Classi ike tane Generic argumenti wrap ediyor
// D: Data -> Successte list of Count ve  E: Error -> Sepesifik type of error
// TODO : https://www.youtube.com/watch?v=MiLN2vs2Oe0 Error Handling Class detaylari
sealed interface Result<out D, out E: Error> {
//    val data nonnullable'dir
    data class Success<out D>(val data: D): Result<D, Nothing>
//    val error nullable'dir
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>