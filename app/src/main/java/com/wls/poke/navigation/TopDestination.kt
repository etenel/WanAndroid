package com.wls.poke.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.wls.poke.R
import com.wls.poke.ui.favorite.navigation.favoriteRoute
import com.wls.poke.ui.home.navigation.homeRoute
import com.wls.poke.ui.person.navigation.personRoute

enum class TopDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route:String="",
) {
    HOME(
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route= homeRoute

    ),
    FAVORITE(
        selectedIcon = Icons.Rounded.Star,
        unselectedIcon = Icons.Outlined.Star,
        iconTextId = R.string.favorite,
        titleTextId = R.string.favorite,
        route= favoriteRoute,
    ),
    PERSON(
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person,
        iconTextId = R.string.person,
        titleTextId = R.string.person,
        route= personRoute,
    ),


}