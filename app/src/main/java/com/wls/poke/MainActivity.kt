package com.wls.poke

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wls.base.BaseActivity
import com.wls.poke.http.ConnectivityManagerNetworkMonitor
import com.wls.poke.ui.WanApp
import com.wls.poke.ui.theme.WanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val connectivityManagerNetworkMonitor = ConnectivityManagerNetworkMonitor.networkMonitor
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState: MainActivityViewModel.UIState by mutableStateOf(MainActivityViewModel.UIState.Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()

            }
        }
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityViewModel.UIState.Loading -> true
                else -> false
            }
        }
        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()
        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)
            WanTheme(darkTheme = darkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    WanApp(
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        connectivityManagerNetworkMonitor = connectivityManagerNetworkMonitor
                    )

                }
            }
        }
    }

    @Composable
    private fun shouldUseDarkTheme(uiState: MainActivityViewModel.UIState): Boolean =
        when (uiState) {
            MainActivityViewModel.UIState.Loading -> isSystemInDarkTheme()
            else -> isSystemInDarkTheme()
        }

}