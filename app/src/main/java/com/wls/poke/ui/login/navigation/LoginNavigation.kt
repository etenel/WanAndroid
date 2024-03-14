package com.wls.poke.ui.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wls.poke.ui.login.LoginRoute

const val loginRoute = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null){
    navigate(loginRoute,navOptions)
}


fun NavGraphBuilder.loginScreen(
    paddingValues: PaddingValues,
    forgetPassword: () -> Unit,
    regist: () -> Unit
) {
    composable(loginRoute) {
        LoginRoute(paddingValues = paddingValues, forgetPassword =forgetPassword , registry = regist)
    }
}