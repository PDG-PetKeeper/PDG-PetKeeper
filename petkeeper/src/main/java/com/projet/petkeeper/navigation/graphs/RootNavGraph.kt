package com.projet.petkeeper.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Navigation graph for the root level of the application.
 *
 * @param navController The navigation controller for handling navigation within this graph.
 */
@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        //authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            //HomeScreen()
        }
    }
}

/**
 * Object containing route constants for navigation graphs.
 */
object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}