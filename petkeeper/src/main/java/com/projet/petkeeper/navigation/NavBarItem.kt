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
    data object SearchRoot: NavBarItem(
        route = "SEARCH",
        title = "SearchRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.Search),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.Search),
        hasNews = false
    )

    data object ChatRoot : NavBarItem(
        route = "CHAT",
        title = "ChatRoot",
        selectedIcon = IconResource.fromDrawableResource(R.drawable.filled_chat_24),
        unselectedIcon = IconResource.fromDrawableResource(R.drawable.outline_chat_24),
        hasNews = false
    )

    data object DashboardRoot : NavBarItem(
        route = "DASHBOARD",
        title = "DashboardRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.List),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.List),
        hasNews = false
    )

    data object ProfileRoot : NavBarItem(
        route = "PROFILE",
        title = "ProfileRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.AccountCircle),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.AccountCircle),
        hasNews = false
    )
    companion object ItemList{
        fun getNavBarItemList(): List<NavBarItem> {
            return listOf(
                SearchRoot,
                ChatRoot,
                DashboardRoot,
                ProfileRoot
            )
        }
    }
}
