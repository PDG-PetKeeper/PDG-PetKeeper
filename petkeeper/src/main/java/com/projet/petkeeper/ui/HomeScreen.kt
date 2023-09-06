package com.projet.petkeeper.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projet.petkeeper.data.UserModel
import com.projet.petkeeper.navigation.NavBar
import com.projet.petkeeper.navigation.graphs.HomeNavGraph

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    userModel: UserModel?,
    onSignOut: () -> Unit
) {

    val viewModel: PetKeeperUIViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        bottomBar = {
            if(uiState.mustShowNavBar)
                NavBar(navController, viewModel)
        }
    ) {paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            HomeNavGraph(navController, viewModel, userModel, onSignOut)
        }
    }
}