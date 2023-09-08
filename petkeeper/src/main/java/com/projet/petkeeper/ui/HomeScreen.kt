package com.projet.petkeeper.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.navigation.NavBar
import com.projet.petkeeper.navigation.graphs.HomeNavGraph

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    userData: UserData?,
    onSignOut: () -> Unit
) {

    Log.e("init", "HomeScreen was called")

    val viewModel = PetKeeperUIViewModel.ViewModel

    if (userData != null) {
        viewModel.userData = userData
    }

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
            if (userData != null) {
                HomeNavGraph(navController, viewModel, userData, onSignOut)
            } else {
                onSignOut()
            }
        }
    }
}