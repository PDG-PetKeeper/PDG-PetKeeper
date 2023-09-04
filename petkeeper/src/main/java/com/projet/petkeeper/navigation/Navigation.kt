package com.projet.petkeeper.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
){
    val navItems = NavItem.getNavBarItemList()
    navItems.find { navItem -> navItem.title == "Dashboard" }?.let {
        NavHost(
        navController = navController,
        startDestination = it.title
    ){

    }
    }
}