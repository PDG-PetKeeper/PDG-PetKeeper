package com.projet.petkeeper.ui

import com.projet.petkeeper.navigation.NavItem


data class GeneralUIState(
    val showNavBar: Boolean = true,
    val navBarItemList: List<NavItem> = NavItem.getNavBarItemList()
    )