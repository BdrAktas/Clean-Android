package com.aktasbdr.cryptotracker.crypto.presentation.model

import androidx.annotation.DrawableRes
import com.aktasbdr.cryptotracker.crypto.domain.Coin
import com.aktasbdr.cryptotracker.util.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale


// bunun amaci Ui'yi olabildince dummy tutmak: Cunku mesela gelen Double valuenun degerini
// farkli bir sekilde display etmek istiyo olabiliriz bu calculationi UI yapmamasi lazim
// ayrica variable'larin icindeki exactly valuesunu da bilmemiz lazim bu yuzden DisplayableNumber classini olusturduk
data class CoinUi(
    val id: String = "",
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    // Iconlar tamamen Ui ile ilgili oldugu Domainle ilgisi olmadigi icin sadece burda tanimladik
    @DrawableRes val iconRes: Int
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

// Domain'den gelen Coin modelini CoinUi'ye ceviriyoruz
fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd.toDisplayableNumber(),
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}

fun Double.toDisplayableNumber(): DisplayableNumber {
    // burda Locale kullanmamizin sebebi formatlarken farklilik gostermesi:
    // Mesela US 3 digitten sonra , decimalden sonra . kullaniyor ->US formats as "1,234.56" In Germany  "1.234,56"
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
       // decimalden sonra exactly 2 digit olucak
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}