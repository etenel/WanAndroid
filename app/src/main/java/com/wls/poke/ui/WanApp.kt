@file:OptIn(
    ExperimentalMaterial3Api::class,
)

package com.wls.poke.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.wls.poke.R
import com.wls.poke.http.ConnectivityManagerNetworkMonitor
import com.wls.poke.navigation.TopDestination
import com.wls.poke.navigation.WanNavHost


@Composable
fun WanApp(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor,
    appState: AppState = rememberAppState(
        windowSizeClass = windowSizeClass,
        networkMonitor = connectivityManagerNetworkMonitor,
        navController=navController
    ),

) {

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val notConnectMessage = stringResource(id = R.string.not_connected)
    val snackbarHostState = appState.snackbarHostState
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectMessage,
                duration = SnackbarDuration.Short
            )
        }
    }

   Box(modifier = Modifier.fillMaxSize()) {

       val destination = appState.currentTopLevelDestination
       Column {
           if (destination != null) {
               CenterAlignedTopAppBar(title = {
                   Text(
                       text = stringResource(id = destination.titleTextId),
                       style = MaterialTheme.typography.titleMedium
                   )
               }, actions = {


               }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                   containerColor = colorScheme.primaryContainer,
               )
               )
           }
           Row(
               Modifier
                   .weight(1f)
                   .windowInsetsPadding(
                       WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                   )
           ) {
               if (appState.shouldShowNavRail && appState.currentTopLevelDestination != null) {
                   WanRail(
                       destinations = appState.destinations,
                       onItemClick = appState::navigateToTopDestination,
                       currentDestination = appState.currentDestination
                   )
               }

               WanNavHost(
                   modifier = Modifier.fillMaxSize(),
                   navController = appState.navController,
                   onShowSnackbar = { message, action ->
                       snackbarHostState.showSnackbar(
                           message = message,
                           actionLabel = action, duration = SnackbarDuration.Short,
                       ) == SnackbarResult.ActionPerformed
                   },
               )
           }
           if (appState.shouldShowBottomBar && appState.currentTopLevelDestination != null) {
               WanBottomBar(
                   destinations = appState.destinations,
                   onItemClick = appState::navigateToTopDestination,
                   currentDestination = appState.currentDestination,
               )
           }
       }
   }

    SnackbarHost(snackbarHostState)
}

@Composable
private fun WanBottomBar(
    destinations: List<TopDestination>,
    onItemClick: (topDestination: TopDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar(containerColor = colorScheme.onPrimary) {
        destinations.forEach { destination ->
            val selected: Boolean = currentDestination?.hierarchy?.any {
                it.route?.contains(destination.route) ?: false
            } ?: false
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) onItemClick(destination)
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
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = colorScheme.secondary.copy(alpha = 0.8f),
                    unselectedTextColor = colorScheme.secondary.copy(alpha = 0.8f),
                ),
            )
        }
    }

}
@Composable
private fun WanRail(
    destinations: List<TopDestination>,
    onItemClick: (topDestination: TopDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationRail(containerColor = colorScheme.onPrimary) {
        destinations.forEach { destination ->
            val selected: Boolean = currentDestination?.hierarchy?.any {
                it.route?.contains(destination.route) ?: false
            } ?: false
            NavigationRailItem(
                selected = selected,
                onClick = {
                    if (!selected) onItemClick(destination)
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
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = colorScheme.secondary.copy(alpha = 0.8f),
                    unselectedTextColor = colorScheme.secondary.copy(alpha = 0.8f),
                ),
            )
        }
    }

}




