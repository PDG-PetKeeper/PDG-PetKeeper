package com.projet.petkeeper.bottomBarScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var hasNews: Boolean,
    var badgeCount: Int = 0
){
    object SearchB : BottomBarScreen(
        route = "SEARCH",
        title = "SEARCH",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        hasNews = false
    )

    object ChatB : BottomBarScreen(
        route = "CHAT",
        title = "CHAT",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Filled.Email,
        hasNews = false
    )

    object AdvertB : BottomBarScreen(
        route = "ADVERT",
        title = "ADVERT",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
        hasNews = false
    )

    object ProfileB : BottomBarScreen(
        route = "PROFILE",
        title = "PROFILE",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false
    )
}




