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
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar() {
    val navItems = NavItem.getNavBarItemList()

    NavigationBar {
        navItems.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                selected = NavItem.currentIndex == index,
                onClick = {
                    NavItem.currentIndex = index
                    // navController.navigate(item.title)
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
                            imageVector = if (index == NavItem.currentIndex) {
                                navBarItem.selectedIcon
                            } else navBarItem.unselectedIcon,
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
                NavBar()
            }
        ) {
                paddingValues ->
            Column(
            modifier = Modifier.padding(paddingValues)
        ) {}
        }
    }
}