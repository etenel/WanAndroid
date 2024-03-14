package com.wls.poke.ui.person.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wls.poke.ui.person.Person

const val personRoute = "person_route"
private const val PERSON_GRAPH_PATTERN = "person_graph"


fun NavController.navigateToPersonGraph(navOptions: NavOptions? = null) {
    this.navigate(personRoute, navOptions)
}

fun NavGraphBuilder.personGraph(
    paddingValues: PaddingValues,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation(
        route = PERSON_GRAPH_PATTERN,
        startDestination = personRoute
    ) {
        composable(route = personRoute,
            //deepLinks = listOf(navDeepLink { uriPattern= }),
//        arguments = listOf(navArgument(" "){type= NavType.StringType})
        ) {
            Person()
        }

    }
}