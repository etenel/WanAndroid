package com.wls.poke.ui.favorite

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wls.poke.ui.home.HomeRoute
import com.wls.poke.ui.home.navigation.homeRoute
import com.wls.poke.ui.mainPager

const val favoriteRoute = "favorite_route"
fun NavController.navigateToFavorite(navOptions: NavOptions? = null) {
    this.navigate(favoriteRoute, navOptions)
}

fun NavGraphBuilder.favoriteScreen() {
        composable(route = favoriteRoute,
            //deepLinks = listOf(navDeepLink { uriPattern= }),
//        arguments = listOf(navArgument(" "){type= NavType.StringType})
        ) {
            Favorite()
        }

}