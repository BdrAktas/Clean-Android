package com.aktasbdr.cryptotracker.crypto.data.mappers

import com.aktasbdr.cryptotracker.crypto.data.network.dto.CoinDto
import com.aktasbdr.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
        // mesela time olsaydi burda timeFormatter ile parse edebilirdik
        // boylece domain katmaninda istedigimiz data typeini kullanabilirdik
    )
}