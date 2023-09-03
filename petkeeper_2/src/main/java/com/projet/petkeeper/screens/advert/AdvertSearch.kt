package com.projet.petkeeper.screens.advert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRootScreen(name: String, onClick: () -> Unit){
    PetkeeperTheme {
        Scaffold(
            topBar = {
                SearchBar(
                    query = "",
                    onQueryChange = {

                    },
                    onSearch = {

                    },
                    active = false,
                    onActiveChange = {

                    },
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "Search for my jobs")
                    },
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "search")
                    }
                ) {

                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(Icons.Filled.Add, "Add a new advert")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DashboardJobCard()
                DashboardJobCard()
            }
        }
    }
}

@Composable
fun DashboardJobCard(){
    ListItem(
        headlineContent = { Text(text = "A Job")},
        leadingContent = {
            Icon(Icons.Filled.Favorite, contentDescription = "fav" )
        }
    )
    Divider()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardRootScreenPreview() {
    PetkeeperTheme {
        DashboardRootScreen("advert", onClick = {})
    }
}
