package com.projet.petkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.ui.navBar.NavBarItem
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetkeeperTheme {

                val items = listOf(
                    NavBarItem(
                        title = "Search",
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search,
                        hasNews = false
                    ),
                    NavBarItem(
                        title = "Chat",
                        selectedIcon = ImageVector.vectorResource(R.drawable.filled_chat_24),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.outline_chat_24),
                        hasNews = false
                    ),
                    NavBarItem(
                        title = "Ads list",
                        selectedIcon = Icons.Filled.List,
                        unselectedIcon = Icons.Outlined.List,
                        hasNews = false
                    ),
                    NavBarItem(
                        title = "Account",
                        selectedIcon = Icons.Filled.AccountCircle,
                        unselectedIcon = Icons.Outlined.AccountCircle,
                        hasNews = false
                    ),
                )
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, navBarItem ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            // navControler.navigate(item.title)
                                        },
//                                        label = { Text(text = navBarItem.title) },
//                                        alwaysShowLabel = false,
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
                                                    imageVector = if (index == selectedItemIndex) {
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
                    ) {
                        paddingValues -> Column(modifier =Modifier.padding(paddingValues) ) {}
                    }
                }
            }
        }
    }
}