package com.wls.poke.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wls.poke.ui.articledetail.navigation.articleDetailScreen
import com.wls.poke.ui.articledetail.navigation.navigateToArticleDetail
import com.wls.poke.ui.favorite.navigation.favoriteGraph
import com.wls.poke.ui.home.navigation.homeRoute
import com.wls.poke.ui.home.navigation.homeScreen
import com.wls.poke.ui.home.navigation.navigateToBanner
import com.wls.poke.ui.login.navigation.loginScreen
import com.wls.poke.ui.login.navigation.navigateToRegister
import com.wls.poke.ui.login.navigation.registerScreen
import com.wls.poke.ui.person.navigation.personGraph


@Composable
fun WanNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onShowSnackbar = onShowSnackbar,
            onBannerClick = navController::navigateToBanner,
            onArticleItemClick = navController::navigateToArticleDetail,

        )
        favoriteGraph(
            onShowSnackbar = onShowSnackbar,
            onItemClick = navController::navigateToArticleDetail,
            nestedGraphs = {
                articleDetailScreen(onBackClick = navController::popBackStack)
            })
        personGraph( onShowSnackbar)
        loginScreen( forgetPassword = {
            //   navController.navigateToForgetPassword()
        }, register = {
               navController.navigateToRegister()
        })
        registerScreen()

    }

}