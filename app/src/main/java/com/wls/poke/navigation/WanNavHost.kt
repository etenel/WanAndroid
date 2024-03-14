package com.wls.poke.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wls.poke.ui.favorite.navigation.favoriteGraph
import com.wls.poke.ui.home.navigation.homeRoute
import com.wls.poke.ui.home.navigation.homeScreen
import com.wls.poke.ui.home.navigation.navigateToBanner
import com.wls.poke.ui.login.navigation.loginScreen
import com.wls.poke.ui.person.navigation.personGraph

@Composable
fun WanNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onShowSnackbar = onShowSnackbar,
            onBannerClick = navController::navigateToBanner,
            paddingValues
        )
        favoriteGraph(paddingValues, onShowSnackbar = onShowSnackbar) { route ->
            navController.navigate(route = route) {
                launchSingleTop = true
            }
        }
        personGraph(paddingValues, onShowSnackbar)
        loginScreen(paddingValues = paddingValues, forgetPassword = {
         //   navController.navigateToForgetPassword()
        }, regist = {
         //   navController.navigateToRegister()
        })

    }

}