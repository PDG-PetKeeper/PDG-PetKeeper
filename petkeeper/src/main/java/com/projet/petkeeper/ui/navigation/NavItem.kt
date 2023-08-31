package com.projet.petkeeper.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.projet.petkeeper.R

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var hasNews: Boolean,
    var badgeCount: Int = 0
){
    companion object Items{
        @Composable
        fun getList(): List<NavItem> {
            return listOf(
                NavItem(
                    title = "Search",
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search,
                    hasNews = false
                ),
                NavItem(
                    title = "Chat",
                    selectedIcon = ImageVector.vectorResource(R.drawable.filled_chat_24),
                    unselectedIcon = ImageVector.vectorResource(R.drawable.outline_chat_24),
                    hasNews = false
                ),
                NavItem(
                    title = "Ads list",
                    selectedIcon = Icons.Filled.List,
                    unselectedIcon = Icons.Outlined.List,
                    hasNews = false
                ),
                NavItem(
                    title = "Account",
                    selectedIcon = Icons.Filled.AccountCircle,
                    unselectedIcon = Icons.Outlined.AccountCircle,
                    hasNews = false
                ),
            )
        }
    }
}
