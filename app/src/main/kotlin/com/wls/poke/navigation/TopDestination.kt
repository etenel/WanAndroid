package com.wls.poke.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.wls.poke.R
import com.wls.poke.ui.favorite.favoriteRoute
import com.wls.poke.ui.home.navigation.homeRoute

enum class TopDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route:String="",
) {
    HOME(
        selectedIcon = Icons.Rounded.Air,
        unselectedIcon = Icons.Outlined.Air,
        iconTextId = R.string.app_name,
        titleTextId = R.string.home,
        route= homeRoute

    ),
    FAVORITE(
        selectedIcon = Icons.Rounded.Security,
        unselectedIcon = Icons.Outlined.Security,
        iconTextId = R.string.app_name,
        titleTextId = R.string.favorite,
        route= favoriteRoute,
    ),
    PERSON(
        selectedIcon = Icons.Rounded.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconTextId = R.string.app_name,
        titleTextId = R.string.person,
        route="MAIN_GER"
    ),
    MAIN_PA(
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person,
        iconTextId = R.string.app_name,
        titleTextId = R.string.app_name,
        route = "MAIN_PA"
    ),

}