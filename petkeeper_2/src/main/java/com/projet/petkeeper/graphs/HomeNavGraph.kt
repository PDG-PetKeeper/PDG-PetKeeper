package com.projet.petkeeper.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

import com.projet.petkeeper.bottomBarScreen.*
import com.projet.petkeeper.screens.ScreenContent


// nav graph depuis home.
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Search.route
    ) {

        // va vers le graph de search
        composable(route = BottomBarScreen.Search.route) {
            ScreenContent(
                name = BottomBarScreen.Search.route,
                onClick = {navController.navigate(Graph.SEARCH)

                }
            )
        }

        // va vers le graph de chat
        composable(route = BottomBarScreen.Chat.route) {
            ScreenContent(
                name = BottomBarScreen.Chat.route,
                onClick = {navController.navigate(Graph.CHAT)

                }
            )
        }

        // va vers le graph de advert
        composable(route = BottomBarScreen.Advert.route) {
            ScreenContent(
                name = BottomBarScreen.Advert.route,
                onClick = { navController.navigate(Graph.ADVERT)}
            )
        }

        // va vers le graph de profile
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(
                name = BottomBarScreen.Profile.route,
                onClick = { navController.navigate(Graph.PROFILE)}
            )
        }
        AdvertNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.AdvertNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.ADVERT,
        startDestination = AdvertScreen.Information.route
    ) {
        composable(route = AdvertScreen.Information.route) {
            ScreenContent(name = AdvertScreen.Information.route) {
                navController.navigate(AdvertScreen.PostAd.route)
            }
        }
        composable(route = AdvertScreen.PostAd.route) {
            ScreenContent(name = AdvertScreen.PostAd.route) {
                navController.popBackStack(
                    route = AdvertScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class AdvertScreen(val route: String) {
    object Information : AdvertScreen(route = "CREATE_ADVERT")
    object PostAd : AdvertScreen(route = "POST_ADVERT")
}