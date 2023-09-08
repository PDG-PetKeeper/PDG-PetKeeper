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
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.navigation.NavBarItem
import com.projet.petkeeper.profile.ProfileScreen
import com.projet.petkeeper.search.SearchRootScreen
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.PetKeeperUIViewModel


// nav graph depuis home.
@SuppressLint("NewApi")
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    userData: UserData,
    onSignOut: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = NavBarItem.DashboardRoot.route
    ) {
        //assigne les controlleurs de nav pour chaque graph
        searchNavGraph(navController, viewModel, uiState, userData)
        chatNavGraph(navController, viewModel, uiState, userData)
        dashboardNavGraph(navController, viewModel, uiState, userData)
        profileNavGraph(navController, viewModel, uiState, userData, onSignOut)
    }
}


fun NavGraphBuilder.searchNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState,
    userData: UserData
) {
    navigation(
        route = NavBarItem.SearchRoot.route,
        startDestination = SearchScreenRoutes.Root.route
    ) {
        composable(route = SearchScreenRoutes.Root.route) {
            SearchRootScreen(
                uiState = uiState,
                onSearch = {
                    viewModel.searchJobs(it)
                    navController.navigate(SearchScreenRoutes.Root.route)
                },
                onJobClick = {

                }
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
    uiState: PetKeeperUIState,
    userData: UserData
) {
    navigation(
        route = NavBarItem.ChatRoot.route,
        startDestination = ChatScreenRoutes.Root.route
    ) {
        composable(route = ChatScreenRoutes.Root.route) {
            ChatRootScreen(
               uiState = uiState,
               userData = userData,
               onChatClick = {userPair, otherUserData ->
                   viewModel.updateCurrentMessages(userPair, otherUserData)
                   navController.navigate(route = ChatScreenRoutes.SelectedChat.route)
                   viewModel.hideNavBar()
               },
                fetchUserData = { userId, fetch ->
                    viewModel.fetchUserDataFromUserId(userId, fetch)
                }
            )
        }

        composable(route = ChatScreenRoutes.SelectedChat.route) {
            // TODO
        }
    }

}


fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState,
    userData: UserData
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
                uiState = uiState,
                onBackClick = {
                    viewModel.showNavBar()
                    viewModel.updateSelectedJob(null)
                    navController.popBackStack(
                        route = DashboardScreenRoutes.Root.route,
                        inclusive = false
                    )
                },
                onEditClick = {
                    navController.navigate(route = DashboardScreenRoutes.JobCreation.route)
                }
            )
        }

        composable(route = DashboardScreenRoutes.JobCreation.route) {
            CreateJob(
                uiState = uiState,
                onBackClick = {
                    viewModel.showNavBar()
                    viewModel.updateSelectedJob(null)
                    navController.popBackStack(
                        route = DashboardScreenRoutes.Root.route,
                        inclusive = false
                    )
                },
                onPublishClick = { jobData: JobData ->
                    viewModel.showNavBar()
                    navController.navigate(route = DashboardScreenRoutes.Root.route)
                    viewModel.dashboardInit()
                },
                userData
            )
        }
    }
}

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    viewModel: PetKeeperUIViewModel,
    uiState: PetKeeperUIState,
    userData: UserData,
    onSignOut: () -> Unit
) {
    navigation(
        route = NavBarItem.ProfileRoot.route,
        startDestination = ProfileScreenRoutes.Root.route
    ) {
        composable(route = ProfileScreenRoutes.Root.route) {
            // edit page
            ProfileScreen(
                userData =  userData,
                onSignOut = {
                   onSignOut()
                   viewModel.changeNavBarCurrentIndex(2)
               }
            )

        }
    }
}


sealed class SearchScreenRoutes(val route: String) {
    data object Root : SearchScreenRoutes(route = "SEARCH_MAIN")
    data object SelectedJob : SearchScreenRoutes(route = "SELECTED_SEARCH")
}

sealed class ChatScreenRoutes(val route: String) {
    data object Root : ChatScreenRoutes(route = "CHAT_MAIN")
    data object SelectedChat : ChatScreenRoutes(route = "SELECTED_CHAT")
}

sealed class DashboardScreenRoutes(val route: String) {
    data object Root : DashboardScreenRoutes(route = "DASHBOARD_ROOT")
    data object JobLook : DashboardScreenRoutes(route = "JOB_LOOK")
    data object JobCreation : DashboardScreenRoutes(route = "JOB_CREATION")
}

sealed class ProfileScreenRoutes(val route: String) {
    data object Root : ProfileScreenRoutes(route = "PROFILE_ROOT")
}

