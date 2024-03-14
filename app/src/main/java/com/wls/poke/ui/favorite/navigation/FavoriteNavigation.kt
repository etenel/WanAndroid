package com.wls.poke.ui.favorite.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wls.poke.ui.favorite.Favorite

const val favoriteRoute = "favorite_route"
private const val FAVORITE_GRAPH_PATTERN = "favorite_graph"


fun NavController.navigateToFavoriteGraph(navOptions: NavOptions? = null) {
    this.navigate(FAVORITE_GRAPH_PATTERN, navOptions)
}

fun NavGraphBuilder.favoriteGraph(
    paddingValues: PaddingValues,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onItemClick: (String) -> Unit
) {

    navigation(
        route = FAVORITE_GRAPH_PATTERN,
        startDestination = favoriteRoute
    ) {
        composable(route = favoriteRoute,
            //deepLinks = listOf(navDeepLink { uriPattern= }),
//        arguments = listOf(navArgument(" "){type= NavType.StringType})
        ) {
            Favorite()
        }

    }
}