package com.aktasbdr.cryptotracker.core.domain.util

// Bunlara gore IU'da gostericez artik Error classinda bunlar var
enum class NetworkError : Error {
    NO_INTERNET,
    SERIALIZATION,
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    CLIENT_ERROR,
    INTERNAL_SERVER_ERROR,
    BAD_GATEWAY,
    SERVICE_UNAVAILABLE,
    GATEWAY_TIMEOUT,
    SERVER_ERROR,
    UNKNOWN
}
