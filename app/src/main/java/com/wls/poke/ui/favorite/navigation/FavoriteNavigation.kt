package com.wls.poke.ui.favorite

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wls.poke.entity.CollectArticleEntity

const val favoriteRoute = "favorite_route"
private const val FAVORITE_GRAPH_PATTERN = "favorite_graph"


fun NavController.navigateToFavoriteGraph(navOptions: NavOptions? = null) {
    this.navigate(FAVORITE_GRAPH_PATTERN, navOptions)
}

fun NavGraphBuilder.favoriteGraph(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onItemClick: (CollectArticleEntity.Data) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {

    navigation(
        route = FAVORITE_GRAPH_PATTERN,
        startDestination = favoriteRoute
    ) {
        composable(route = favoriteRoute,
            //deepLinks = listOf(navDeepLink { uriPattern= }),
//        arguments = listOf(navArgument(" "){type= NavType.StringType})
        ) {
            FavoriteRoute( articleDetail=onItemClick)
        }
        nestedGraphs()

    }
}