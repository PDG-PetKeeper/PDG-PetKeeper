package com.projet.petkeeper.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projet.petkeeper.navigation.NavBar
import com.projet.petkeeper.navigation.graphs.HomeNavGraph

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        bottomBar = { NavBar()}
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            HomeNavGraph(navController = navController)
        }
    }
}