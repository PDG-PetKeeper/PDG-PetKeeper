package com.projet.petkeeper.ui

import com.projet.petkeeper.navigation.NavBarItem


data class GeneralUIState(
    val mustShowNavBar: Boolean = true,
    val navBarItemList: List<NavBarItem> = NavBarItem.getNavBarItemList()
)