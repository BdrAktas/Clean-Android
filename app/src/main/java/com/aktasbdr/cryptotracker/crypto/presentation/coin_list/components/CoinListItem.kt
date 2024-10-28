package com.aktasbdr.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aktasbdr.cryptotracker.crypto.domain.Coin
import com.aktasbdr.cryptotracker.crypto.presentation.model.CoinUi
import com.aktasbdr.cryptotracker.crypto.presentation.model.toCoinUi
import com.aktasbdr.cryptotracker.ui.theme.CryptoTrackerTheme
import com.aktasbdr.cryptotracker.ui.theme.onPrimaryDarkHighContrast
import com.aktasbdr.cryptotracker.ui.theme.onPrimaryLightHighContrast

@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
//        Color.White
        onPrimaryLightHighContrast
    } else {
        //onPrimaryDarkHighContrast
//        Color.Black
        onPrimaryDarkHighContrast
    }
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(85.dp)
        )
        Column(
            // burdaki column'in verilen tum alani kullanmasi icin
            // ta ki bir sonraki columna kadar
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
//                color = contentColor
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = coinUi.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
//                color = contentColor
                color = MaterialTheme.colorScheme.onSecondaryContainer

            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                //                color = contentColor
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            PriceChange(
                change = coinUi.changePercent24Hr
            )
        }

    }

}


@PreviewLightDark
@Composable
private fun CoinListItemPreviewStatic() {
    CryptoTrackerTheme() {
        CoinListItem(
            coinUi = previewCoin,
            onClick = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

@PreviewLightDark
//bu ozellik bugli o yuzden kullanmiyorum
//@PreviewDynamicColors
@Composable
private fun CoinListItemPreviewDL() {
    CryptoTrackerTheme(dynamicColor = true, useStaticTheme = false) {
        CoinListItem(
            coinUi = previewCoin,
            onClick = { /*TODO*/ },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

//bircok yerde kullanip Preview edebilmek icin
internal val previewCoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 1241273958896.75,
    priceUsd = 62828.15,
    changePercent24Hr = -0.1
).toCoinUi()

//Other Visibility Modifiers in Kotlin
//public: The default visibility modifier; it allows access from anywhere.
//private: Limits access to the class or file where it’s declared.
//protected: Limits access to the class where it’s declared and its subclasses.
//internal: Limits access to the same module.