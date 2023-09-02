package com.projet.petkeeper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
){
    val navItems = NavItem.getList()
    navItems.find { navItem -> navItem.title == "Dashboard" }?.let {
        NavHost(
        navController = navController,
        startDestination = it.title
    ){

    }
    }
}