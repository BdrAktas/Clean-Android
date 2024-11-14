package com.aktasbdr.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aktasbdr.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.aktasbdr.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.aktasbdr.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.aktasbdr.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Koin'in bize sagladigi koinViewModel functioni ile VM'umuzu aliyoruz
                    val viewModel = koinViewModel<CoinListViewModel>()
                    // VM'umuzundan state'ini aliyoruz
                    //collectAsStateWithLifecycle ile appin Lifecycle'ina baglantili olarak state'ini aliyoruz
                    // yani app background'a gecerse stop collect yapiyoruz
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    CoinListScreen(
                        state = state,
                        //burdaki innerPadding Scaffold'an gelen bir seylerin system UI(status bar, navigation bar) ile overlapping olmasini engelliyor
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTrackerTheme {
        Greeting("Android")
    }
}