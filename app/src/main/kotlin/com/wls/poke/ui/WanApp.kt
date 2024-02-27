@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)

package com.wls.poke.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wls.poke.R
import com.wls.poke.http.ConnectivityManagerNetworkMonitor
import com.wls.poke.navigation.TopDestination
import com.wls.poke.navigation.TopDestination.*
import com.wls.poke.ui.favorite.Favorite
import com.wls.poke.ui.favorite.favoriteScreen
import com.wls.poke.ui.favorite.navigateToFavorite
import com.wls.poke.ui.home.HomeRoute
import com.wls.poke.ui.home.navigation.HOME_GRAPH_PATTERN
import com.wls.poke.ui.home.navigation.homeGraph
import com.wls.poke.ui.home.navigation.navigateToHomeGraph
import kotlinx.coroutines.launch

@Composable
fun WanApp(
    windowSizeClass: WindowSizeClass,
    connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor,
    appState: AppState = rememberAppState(
        windowSizeClass = windowSizeClass,
        networkMonitor = connectivityManagerNetworkMonitor
    )
) {

    var enableScroll by rememberSaveable {
        mutableStateOf(true)
    }

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val notConnectMessage = stringResource(id = R.string.not_connected)
    val destinations = values().asList()
    val pagerState =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f,
            pageCount = { destinations.size })
    LaunchedEffect(isOffline) {
        if (isOffline) {
            appState.snackbarHostState.showSnackbar(
                message = notConnectMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }
    Scaffold(snackbarHost = {
        SnackbarHost(appState.snackbarHostState)
    },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0,0,0,0),
        bottomBar = {
            if (appState.shouldShowBottomBar && appState.currentTopLevelDestination!=null) {
                WanBottomBar(enableScroll, destinations, appState,pagerState)
            }
        }) {
        enableScroll = false
        Row(
            Modifier
                .fillMaxSize()
                .padding(it)
                .consumeWindowInsets(it)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                )) {
            if (appState.shouldShowNavRail && appState.currentTopLevelDestination!=null){

            }
            Column(
                Modifier.fillMaxSize()
            ) {
                val destination = appState.currentTopLevelDestination
                if (destination != null) {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            text = stringResource(id = destination.titleTextId),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }, actions = {

                    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorScheme.inversePrimary,
                    )
                    )
                }
                if (enableScroll) {
                    scrollScreen(destinations, appState, Modifier.weight(1f),pagerState)
                } else {
                    unScrollScreen(appState, destinations, Modifier.weight(1f))
                }
            }

        }

    }
}

@Composable
private fun WanBottomBar(
    enableScroll: Boolean,
    destinations: List<TopDestination>,
    appState: AppState,
    pagerState: PagerState
) {
    if (enableScroll) {
        NavigationBar {
            destinations.forEachIndexed { index, pager ->
                val selected = pagerState.currentPage == index
                NavigationBarItem(
                    modifier = Modifier.background(Color.Transparent),
                    selected = selected,
                    onClick = {
                        appState.coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected) pager.selectedIcon else pager.unselectedIcon,
                            contentDescription = stringResource(id = pager.iconTextId),
                        )

                    },
                    label = {
                        Text(
                            text = stringResource(id = pager.titleTextId),
                        )
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = colorScheme.inversePrimary,
                        unselectedTextColor = colorScheme.inversePrimary,
                        disabledIconColor = Color.Unspecified,
                        disabledTextColor = Color.Unspecified,
                    ),
                )

            }
        }
    } else {
        NavigationBar(containerColor = colorScheme.tertiaryContainer) {
            destinations.forEachIndexed { index, destination ->
                val selected: Boolean = appState.currentDestination?.hierarchy?.any {
                    it.route?.contains(destination.route) ?: false
                } ?: false
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        val navOptions = navOptions {
                            popUpTo(appState.navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        when (destination) {
                            HOME -> appState.navController.navigateToHomeGraph(navOptions)
                            FAVORITE -> appState.navController.navigateToFavorite(navOptions)
//                            MAIN_GER -> TODO()
//                            MAIN_PA -> TODO()
                            else -> appState.navController.navigate(destination.route) {
                                popUpTo(appState.navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }


                    },
                    icon = {
                        Icon(
                            imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                            contentDescription = null,
                        )

                    },
                    label = {
                        Text(
                            text = stringResource(id = destination.iconTextId),
                        )
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = colorScheme.inversePrimary,
                        unselectedTextColor = colorScheme.inversePrimary,
                        disabledIconColor = Color.Unspecified,
                        disabledTextColor = Color.Unspecified,
                    ),
                )
            }
        }
    }
}

@Composable
private fun unScrollScreen(
    appState: AppState,
    destinations: List<TopDestination>,
    modifier: Modifier
) {
    val currentDestination = appState.currentDestination

    Column(modifier = modifier) {
        Row(Modifier.weight(1f)) {
            NavHost(
                navController = appState.navController,
                startDestination = HOME_GRAPH_PATTERN,
            ) {
                destinations.forEachIndexed { index, topDestination ->
                    if (index > 1) {
                        composable(topDestination.route) {
                            mainPager(page = index.toString())
                        }
                    }
                }
                favoriteScreen()
                homeGraph(onShowSnackbar(appState)) {
                    appState.navController.navigate(it) {
                        launchSingleTop = true
                    }
                }


            }

        }

    }
}

//snackbar吐司提示
private fun onShowSnackbar(appState: AppState): suspend (String, String?) -> Boolean =
    { message, action ->
        appState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = action, duration = SnackbarDuration.Short,
        ) == SnackbarResult.ActionPerformed
    }

@Composable
private fun scrollScreen(
    pagers: List<TopDestination>,
    appState: AppState,
    modifier: Modifier,
    pagerState: PagerState,
) {
    HorizontalPager(state = pagerState, modifier = modifier) { page ->
        when (page) {
            0 -> HomeRoute(onShowSnackbar = onShowSnackbar(appState), {
                appState.navController.navigate(it)
            })

            1 -> Favorite()
            2 -> mainPager(page.toString())
            3 -> mainPager(page.toString())
            4 -> mainPager(page.toString())
        }
    }

}

@Composable
fun mainPager(page: String) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Button(onClick = {

        }) {
            Text(text = "pager${page}")
        }
    }


}