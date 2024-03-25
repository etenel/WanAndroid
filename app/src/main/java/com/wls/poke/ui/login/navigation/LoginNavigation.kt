package com.wls.poke.ui.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wls.poke.ui.login.LoginRoute
import com.wls.poke.ui.login.RegisterRoute

const val loginRoute = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null){
    navigate(loginRoute,navOptions)
}


fun NavGraphBuilder.loginScreen(
    forgetPassword: () -> Unit,
    register: () -> Unit,
    onBack:()->Unit
) {
    composable(loginRoute) {
        LoginRoute( forgetPassword =forgetPassword , registry = register, onBack = onBack)
    }
}

const val registerRoute = "register_route"

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    this.navigate(registerRoute, navOptions)
}

fun NavGraphBuilder.registerScreen() {
    composable(
        route = registerRoute,
        //deepLinks = listOf(navDeepLink { uriPattern= }),
//        arguments = listOf(navArgument(" "){type= NavType.StringType})
    ) {
        RegisterRoute()
    }
}