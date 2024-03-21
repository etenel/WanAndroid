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
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.wls.base.BaseActivity
import com.wls.base.entity.ResultState
import com.wls.poke.http.ConnectivityManagerNetworkMonitor
import com.wls.poke.ui.WanApp
import com.wls.poke.ui.login.navigation.navigateToLogin
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
    private lateinit var navController: NavHostController
    private lateinit var uiState:ResultState<Unit>
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
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
              ResultState.Loading -> true
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
                    navController = rememberNavController()
                    WanApp(
                        navController = navController,
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        connectivityManagerNetworkMonitor = connectivityManagerNetworkMonitor
                    )

                }
            }
        }


    }

    //跳转登录界面
    override fun login() {
        navController.navigateToLogin()
    }

    @Composable
    private fun shouldUseDarkTheme(uiState: ResultState<Unit>): Boolean =
        when (uiState) {
           ResultState.Loading -> isSystemInDarkTheme()
            else -> isSystemInDarkTheme()
        }

}