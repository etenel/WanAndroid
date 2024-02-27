package com.wls.poke.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wls.poke.http.NetworkMonitor
import com.wls.poke.navigation.TopDestination
import com.wls.poke.ui.favorite.favoriteRoute
import com.wls.poke.ui.home.navigation.homeRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    val snackbarHostState: SnackbarHostState,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val currentTopLevelDestination: TopDestination?
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> TopDestination.HOME
            favoriteRoute -> TopDestination.FAVORITE
           // personRoute -> TopDestination.PERSON
            else -> null
        }
    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
): AppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
        networkMonitor,
        snackbarHostState,
    ) {
        AppState(
            navController,
            coroutineScope,
            windowSizeClass,
            networkMonitor,
            snackbarHostState,
        )
    }
}