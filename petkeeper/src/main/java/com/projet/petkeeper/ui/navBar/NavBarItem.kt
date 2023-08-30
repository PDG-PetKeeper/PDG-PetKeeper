package com.projet.petkeeper.ui.navBar

import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int = 0
)
