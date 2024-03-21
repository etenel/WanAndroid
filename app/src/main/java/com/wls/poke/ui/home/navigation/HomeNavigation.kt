package com.wls.poke.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.ui.home.HomeRoute

const val homeRoute = "home_route"
fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}
fun NavController.navigateToBanner(url:String){
//    navigate("banner/$url"){
//        launchSingleTop=true
//    }
}
fun NavGraphBuilder.homeScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBannerClick: (String) -> Unit,
    onArticleItemClick: (article: HomeArticleEntity.Data) -> Unit,

) {
        composable(route = homeRoute,
            //deepLinks = listOf(navDeepLink { uriPattern= }),
//        arguments = listOf(navArgument(" "){type= NavType.StringType})
        ) {
            HomeRoute(onShowSnackbar = onShowSnackbar,onBannerClick=onBannerClick, articleDetail =onArticleItemClick )

        }
}