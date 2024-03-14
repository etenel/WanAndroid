package com.wls.poke.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.wls.poke.http.NetworkMonitor
import com.wls.poke.navigation.TopDestination
import com.wls.poke.ui.favorite.navigation.favoriteRoute
import com.wls.poke.ui.home.navigation.homeRoute
import com.wls.poke.ui.home.navigation.navigateToHome
import com.wls.poke.ui.login.navigation.navigateToLogin
import com.wls.poke.ui.person.navigation.personRoute
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
    val destinations: List<TopDestination> = TopDestination.entries
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val currentTopLevelDestination: TopDestination?
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> TopDestination.HOME
            favoriteRoute -> TopDestination.FAVORITE
            personRoute -> TopDestination.PERSON
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

    fun navigateToTopDestination(topDestination: TopDestination) {

        val topNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topDestination) {
            TopDestination.HOME -> {
                navController.navigateToHome(topNavOptions)
            }

            TopDestination.FAVORITE -> {

                    navController.navigateToLogin()

                  //  navController.navigateToFavoriteGraph(topNavOptions)

            }

            TopDestination.PERSON -> {

                    navController.navigateToLogin()

                   // navController.navigateToPersonGraph(topNavOptions)

            }

        }

    }

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