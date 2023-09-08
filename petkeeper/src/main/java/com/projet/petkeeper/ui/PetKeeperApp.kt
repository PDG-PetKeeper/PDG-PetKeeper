package com.projet.petkeeper.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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

/**
 * Composable function representing the entry point of the PetKeeper app once logged in.
 * This function contains navigation components, viewModel declaration and  the "base screen" 8navBar).
 *
 * @param navController The [NavHostController] responsible for navigating between different screens.
 * @param userData The user's data
 * @param onSignOut Callback function to sign the user out.
 */
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    userData: UserData?,
    onSignOut: () -> Unit
) {

    Log.e("init", "HomeScreen was called")

    // Get the ViewModel associated with the PetKeeper UI.
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
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            // Display the HomeNavGraph if user data is available, otherwise call onSignOut.
            if (userData != null) {
                HomeNavGraph(navController, viewModel, userData, onSignOut)
            } else {
                onSignOut()
            }
        }
    }
}