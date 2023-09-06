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
import com.projet.petkeeper.dashboard.CreateJob
import com.projet.petkeeper.dashboard.DashboardRootScreen
import com.projet.petkeeper.dashboard.LookupJob
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
    viewModel: PetKeeperUIViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = NavBarItem.DashboardRoot.route
    ) {
        //assigne les controlleurs de nav pour chaque graph
        searchNavGraph(navController, viewModel, uiState)
        chatNavGraph(navController, viewModel, uiState)
        dashboardNavGraph(navController, viewModel, uiState)
        profileNavGraph(navController, viewModel, uiState)
    }
}


fun NavGraphBuilder.searchNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {
    navigation(
        route = NavBarItem.SearchRoot.route,
        startDestination = SearchScreenRoutes.Information.route
    ) {
        composable(route = SearchScreenRoutes.Information.route) {
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

fun NavGraphBuilder.chatNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {
    navigation(
        route = NavBarItem.ChatRoot.route,
        startDestination = ChatScreenRoutes.Information.route
    ) {
        composable(route = ChatScreenRoutes.Information.route) {
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

fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {

    navigation(
        route = NavBarItem.DashboardRoot.route,
        startDestination = DashboardScreenRoutes.Root.route
    ) {
        composable(route = DashboardScreenRoutes.Root.route) {
            DashboardRootScreen(
                uiState = uiState,
                onJobClick = { jobData: JobData ->
                    viewModel.updateSelectedJob(jobData)
                    viewModel.hideNavBar()
                    navController.navigate(route = DashboardScreenRoutes.JobLook.route)
                },
                onAddClick = {
                    viewModel.hideNavBar()
                    navController.navigate(route = DashboardScreenRoutes.JobCreation.route)
                }
            )
        }

        composable(route = DashboardScreenRoutes.JobLook.route) {
            LookupJob(
                onBackClick = {
                    viewModel.updateSelectedJob(null)
                    viewModel.showNavBar()
                    navController.popBackStack(
                        route = DashboardScreenRoutes.Root.route,
                        inclusive = false,
                        saveState = true
                    )
                },
                onEditClick = {

                }
            )
        }

        composable(route = DashboardScreenRoutes.JobCreation.route) {
            CreateJob(
                onBackClick = {
                    viewModel.updateSelectedJob(null)
                    viewModel.showNavBar()
                    navController.popBackStack(
                        route = DashboardScreenRoutes.Root.route,
                        inclusive = false,
                        saveState = true
                    )
                },
                onPublishClick = { jobData: JobData ->
                    viewModel.addJob(jobData)
                    viewModel.updateSelectedJob(jobData)
                    navController.navigate(route = DashboardScreenRoutes.JobLook.route)
                }
            )
        }
    }
}

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState
) {
    navigation(
        route = NavBarItem.ProfileRoot.route,
        startDestination = ProfileScreenRoutes.Root.route
    ) {
        composable(route = ProfileScreenRoutes.Root.route) {
            // edit page
            ProfileRootScreen(
//                name = ProfileScreen.Information.route
            )
        }
    }
}


sealed class SearchScreenRoutes(val route: String) {
    data object Information : SearchScreenRoutes(route = "SEARCH_MAIN")
    data object SelectedRoutsSearch : SearchScreenRoutes(route = "SELECTED_SEARCH")
}

sealed class ChatScreenRoutes(val route: String) {
    data object Information : ChatScreenRoutes(route = "CHAT_MAIN")
    data object SelectedChat : ChatScreenRoutes(route = "SELECTED_CHAT")
}

sealed class DashboardScreenRoutes(val route: String) {
    data object Root : DashboardScreenRoutes(route = "DASHBOARD_ROOT")
    data object JobLook : DashboardScreenRoutes(route = "JOB_LOOK")
    data object JobCreation : DashboardScreenRoutes(route = "JOB_CREATION")
    data object JobEdit : DashboardScreenRoutes(route = "JOB_EDIT")
}

sealed class ProfileScreenRoutes(val route: String) {
    data object Root : ProfileScreenRoutes(route = "PROFILE_ROOT")
}

