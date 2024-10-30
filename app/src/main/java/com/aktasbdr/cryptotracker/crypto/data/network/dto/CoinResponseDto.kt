package com.aktasbdr.cryptotracker.crypto.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponseDto(
    val data: List<CoinDto>
)