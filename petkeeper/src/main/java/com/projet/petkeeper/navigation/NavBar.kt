package com.projet.petkeeper.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    navController: NavHostController,
    navBarItemList: List<NavBarItem> = NavBarItem.getNavBarItemList()
) {
    NavigationBar {
        navBarItemList.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                selected = NavBarItem.currentIndex == index,
                onClick = {
                    NavBarItem.currentIndex = index
                    navController.navigate(navBarItem.route)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (navBarItem.badgeCount != 0) {
                                Badge {
                                    Text(text = navBarItem.badgeCount.toString())
                                }
                            } else if (navBarItem.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            if (index == NavBarItem.currentIndex)
                                navBarItem.selectedIcon.asPainterResource()
                            else
                                navBarItem.unselectedIcon.asPainterResource(),

                            contentDescription = navBarItem.title
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NavBarPreview() {
    PetkeeperTheme {
        Scaffold(
            bottomBar = {
                NavBar(rememberNavController())
            }
        ) {
                paddingValues ->
            Column(
            modifier = Modifier.padding(paddingValues)
        ) {}
        }
    }
}