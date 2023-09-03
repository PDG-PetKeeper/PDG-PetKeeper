package com.projet.petkeeper.graphs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.projet.petkeeper.bottomBarScreen.BottomBarScreen
import com.projet.petkeeper.screens.DashboardRootScreen
import com.projet.petkeeper.screens.ProfileRootScreen
import com.projet.petkeeper.screens.ScreenContent
import com.projet.petkeeper.screens.SearchRootScreen
import com.projet.petkeeper.ui.chat.chatScreens.SearchScreen


// nav graph depuis home.
@SuppressLint("NewApi")
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Search.route
    ) {

        // va vers le graph de search
        composable(route = BottomBarScreen.Search.route) {

            // à chnager vers SearchContent
            SearchRootScreen(
                name = BottomBarScreen.Search.route,
                onClick = {navController.navigate(Graph.SEARCH)

                }
            )
        }

        // va vers le graph de chat
        composable(route = BottomBarScreen.Chat.route) {
            SearchScreen(
                name = BottomBarScreen.Chat.route,
                onClick = {navController.navigate(Graph.CHAT)

                }
            )

        }

        // va vers le graph de advert
        composable(route = BottomBarScreen.Advert.route) {
            DashboardRootScreen(
                name = BottomBarScreen.Advert.route,
                onClick = { navController.navigate(Graph.ADVERT)}
            )

        }

        // va vers le graph de profile
        composable(route = BottomBarScreen.Profile.route) {
            ProfileRootScreen(
                name = BottomBarScreen.Profile.route,
                //onClick = { navController.navigate(Graph.PROFILE)}
            )

        }
        //assigne les controlleurs de nav pour chaque graph
        SearchNavGraph(navController = navController)
        ChatNavGraph(navController = navController)
        AdvertNavGraph(navController = navController)
        ProfileNavGraph(navController = navController)
    }
}


fun NavGraphBuilder.SearchNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.SEARCH,
        startDestination = SearchScreen.Information.route
    ) {
        composable(route = SearchScreen.Information.route) {
            SearchRootScreen(name = SearchScreen.Information.route) {
                navController.navigate(SearchScreen.SelectedSearch.route)
            }
        }
        composable(route = SearchScreen.SelectedSearch.route) {
            ScreenContent(name = SearchScreen.SelectedSearch.route) {
                navController.popBackStack(
                    route = SearchScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

fun NavGraphBuilder.ChatNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.CHAT,
        startDestination = ChatScreen.Information.route
    ) {
        composable(route = ChatScreen.Information.route) {
            ScreenContent(name = ChatScreen.Information.route) {
                navController.navigate(ChatScreen.SelectedChat.route)
            }
        }
        composable(route = ChatScreen.SelectedChat.route) {
            ScreenContent(name = ChatScreen.SelectedChat.route) {
                navController.popBackStack(
                    route = ChatScreen.Information.route,
                    inclusive = false
                )
            }
        }
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

fun NavGraphBuilder.ProfileNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileScreen.Information.route
    ) {
        composable(route = ProfileScreen.Information.route) {
            // edit page
            ScreenContent(name = ProfileScreen.Information.route) {
            }
        }
    }
}


sealed class SearchScreen(val route: String) {
    object Information : SearchScreen(route = "SEARCH_MAIN")
    object SelectedSearch : SearchScreen(route = "SELETED_SEARCH")
}

sealed class ChatScreen(val route: String) {
    object Information : ChatScreen(route = "CHAT_MAIN")
    object SelectedChat : ChatScreen(route = "SELETED_CHAT")
}

sealed class AdvertScreen(val route: String) {
    object Information : AdvertScreen(route = "DASHBOARD")
    object MyAdvert : AdvertScreen(route = "My_ADVERT")
    object PostAd : AdvertScreen(route = "POST_ADVERT")
}

sealed class ProfileScreen(val route: String) {
    object Information : ProfileScreen(route = "See_Profile")
}

