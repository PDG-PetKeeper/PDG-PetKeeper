package com.projet.petkeeper.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import com.projet.petkeeper.R
import com.projet.petkeeper.ui.IconResource

sealed class NavBarItem(
    val route: String,
    val title: String,
    val selectedIcon: IconResource,
    val unselectedIcon: IconResource,
    var hasNews: Boolean,
    var badgeCount: Int = 0
){
    object searchRoot: NavBarItem(
        route = "SEARCH",
        title = "SearchRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.Search),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.Search),
        hasNews = false
    )

    object chatRoot : NavBarItem(
        route = "CHAT",
        title = "ChatRoot",
        selectedIcon = IconResource.fromDrawableResource(R.drawable.filled_chat_24),
        unselectedIcon = IconResource.fromDrawableResource(R.drawable.outline_chat_24),
        hasNews = false
    )

    object dashboardRoot : NavBarItem(
        route = "DASHBOARD",
        title = "DashboardRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.List),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.List),
        hasNews = false
    )

    object profileRoot : NavBarItem(
        route = "PROFILE",
        title = "ProfileRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.AccountCircle),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.AccountCircle),
        hasNews = false
    )
    companion object Items{
        var currentIndex:Int = 2
        fun getNavBarItemList(): List<NavBarItem> {
            return listOf(
                searchRoot,
                chatRoot,
                dashboardRoot,
                profileRoot
            )
        }
    }
}
