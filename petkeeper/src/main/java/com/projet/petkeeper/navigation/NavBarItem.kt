package com.projet.petkeeper.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import com.projet.petkeeper.R
import com.projet.petkeeper.data.IconResource

/**
 * Enumeration representing items in the navigation bar.
 *
 * @param route The route associated with the navigation item.
 * @param title The title or label of the navigation item.
 * @param selectedIcon The icon resource when the item is selected.
 * @param unselectedIcon The icon resource when the item is not selected.
 * @param hasNews Flag indicating whether the item has new updates or notifications.
 * @param badgeCount The count of badges or notifications for the item.
 */
sealed class NavBarItem(
    val route: String,
    val title: String,
    val selectedIcon: IconResource,
    val unselectedIcon: IconResource,
    var hasNews: Boolean,
    var badgeCount: Int = 0
){
    /**
     * Navigation item for the SearchRoot.
     */
    data object SearchRoot: NavBarItem(
        route = "SEARCH",
        title = "SearchRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.Search),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.Search),
        hasNews = false
    )

    /**
     * Navigation item for the ChatRoot.
     */
    data object ChatRoot : NavBarItem(
        route = "CHAT",
        title = "ChatRoot",
        selectedIcon = IconResource.fromDrawableResource(R.drawable.filled_chat_24),
        unselectedIcon = IconResource.fromDrawableResource(R.drawable.outline_chat_24),
        hasNews = false
    )

    /**
     * Navigation item for the DashboardRoot.
     */
    data object DashboardRoot : NavBarItem(
        route = "DASHBOARD",
        title = "DashboardRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.List),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.List),
        hasNews = false
    )

    /**
     * Navigation item for the ProfileRoot.
     */
    data object ProfileRoot : NavBarItem(
        route = "PROFILE",
        title = "ProfileRoot",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.AccountCircle),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.AccountCircle),
        hasNews = false
    )
    companion object ItemList{
        /**
         * Get a list of all navigation bar items.
         *
         * @return A list of navigation bar items.
         */
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
