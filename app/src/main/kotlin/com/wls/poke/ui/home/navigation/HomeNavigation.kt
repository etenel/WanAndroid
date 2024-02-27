package com.wls.poke.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wls.poke.ui.home.HomeRoute
import com.wls.poke.ui.mainPager

const val homeRoute = "home_route"
 const val HOME_GRAPH_PATTERN="home_graph"
fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH_PATTERN, navOptions)
}

fun NavGraphBuilder.homeGraph(onShowSnackbar: suspend (String, String?) -> Boolean,
                              onBannerClick:(String)->Unit) {

    navigation(route= HOME_GRAPH_PATTERN,
        startDestination = homeRoute){
        composable(route = homeRoute,
            //deepLinks = listOf(navDeepLink { uriPattern= }),
//        arguments = listOf(navArgument(" "){type= NavType.StringType})
        ) {
            HomeRoute(onShowSnackbar = onShowSnackbar,onBannerClick=onBannerClick)
        }
        composable(route="main1"){
            mainPager(page = "main1")
        }
        composable(route="main2"){
            mainPager(page = "main2")
        }
        composable(route="main3"){
            mainPager(page = "main3")
        }
    }


}