package com.projet.petkeeper.navigation.graphs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.projet.petkeeper.chat.ChatRootScreen
import com.projet.petkeeper.dashboard.DashboardRootScreen
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.navigation.NavBarItem
import com.projet.petkeeper.profile.ProfileRootScreen
import com.projet.petkeeper.search.SearchRootScreen
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.PetKeeperUIViewModel


// nav graph depuis home.
@SuppressLint("NewApi")
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    //viewModel: GeneralUIViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = NavBarItem.dashboardRoot.route
    ) {

        // va vers le graph de search
        composable(route = NavBarItem.searchRoot.route) {

            // à chnager vers SearchContent
            SearchRootScreen(
//                name = NavItem.searchRoot.route,
//                onClick = {navController.navigate(Graph.SEARCH) {
//                        popUpTo(Graph.HOME)
//                    }
//                }
            )
        }

        // va vers le graph de chat
        composable(route = NavBarItem.chatRoot.route) {
            SearchRootScreen(
//                name = NavItem.chatRoot.route,
//                onClick = {
//                    navController.navigate(Graph.CHAT)
//                }
            )

        }

        // va vers le graph de advert
        composable(route = NavBarItem.dashboardRoot.route) {
            DashboardRootScreen(
                uiState = uiState,
                onJobClick = {
                    jobData: JobData -> viewModel.updateSelectedJob(jobData)
                    viewModel.hideNavBar()
                }
//                name = NavItem.dashboardRoot.title,
//                onClick = { navController.navigate(Graph.ADVERT)}
            )

        }

        // va vers le graph de profile
        composable(route = NavBarItem.profileRoot.route) {
            ProfileRootScreen(
//                name = NavItem.profileRoot.route,
//                onClick = { navController.navigate(Graph.PROFILE)}
            )

        }
        //assigne les controlleurs de nav pour chaque graph
        SearchNavGraph(navController, viewModel, uiState)
        ChatNavGraph(navController, viewModel, uiState)
        DashboardNavGraph(navController, viewModel, uiState)
        ProfileNavGraph(navController, viewModel, uiState)
    }
}


fun NavGraphBuilder.SearchNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {
    navigation(
        route = Graph.SEARCH,
        startDestination = SearchScreen.Information.route
    ) {
        composable(route = SearchScreen.Information.route) {
            SearchRootScreen(
                //name = SearchScreen.Information.route
                //navController.navigate(SearchScreen.SelectedSearch.route)
            )
        }
//        composable(route = SearchScreen.SelectedSearch.route) {
//            ScreenContent(name = SearchScreen.SelectedSearch.route) {
//                navController.popBackStack(
//                    route = SearchScreen.Information.route,
//                    inclusive = false
//                )
//            }
//        }
    }
}

fun NavGraphBuilder.ChatNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {
    navigation(
        route = Graph.CHAT,
        startDestination = ChatScreen.Information.route
    ) {
        composable(route = ChatScreen.Information.route) {
            ChatRootScreen(
//                name = ChatScreen.Information.route
//                onClick = {
//                    navController.navigate(ChatScreen.SelectedChat.route)
//                }
            )
        }
//        composable(route = ChatScreen.SelectedChat.route) {
//            ScreenContent(name = ChatScreen.SelectedChat.route) {
//                navController.popBackStack(
//                    route = ChatScreen.Information.route,
//                    inclusive = false
//                )
//            }
//        }
    }
}

fun NavGraphBuilder.DashboardNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {

    navigation(
        route = Graph.ADVERT,
        startDestination = AdvertScreen.Information.route
    ) {
        composable(route = AdvertScreen.Information.route) {
                DashboardRootScreen(
                    uiState = uiState,
                    onJobClick = {
                            jobData: JobData -> viewModel.updateSelectedJob(jobData)
                        viewModel.hideNavBar()
                    }
    //                name = NavItem.dashboardRoot.title,
    //                onClick = { navController.navigate(Graph.ADVERT)}
                )

            }
//        composable(route = AdvertScreen.PostAd.route) {
//            ScreenContent(name = AdvertScreen.PostAd.route) {
//                navController.popBackStack(
//                    route = AdvertScreen.Information.route,
//                    inclusive = false
//                )
//            }
//        }
    }
}

fun NavGraphBuilder.ProfileNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {
    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileScreen.Information.route
    ) {
        composable(route = ProfileScreen.Information.route) {
            // edit page
            ProfileRootScreen(
//                name = ProfileScreen.Information.route
            )
        }
    }
}


sealed class SearchScreen(val route: String) {
    object Information : SearchScreen(route = "SEARCH_MAIN")
    object SelectedSearch : SearchScreen(route = "SELECTED_SEARCH")
}

sealed class ChatScreen(val route: String) {
    object Information : ChatScreen(route = "CHAT_MAIN")
    object SelectedChat : ChatScreen(route = "SELECTED_CHAT")
}

sealed class AdvertScreen(val route: String) {
    object Information : AdvertScreen(route = "DASHBOARD_MAIN")
    object MyAdvert : AdvertScreen(route = "MY_ADVERT")
    object PostAd : AdvertScreen(route = "POST_ADVERT")
}

sealed class ProfileScreen(val route: String) {
    object Information : ProfileScreen(route = "See_Profile")
}

