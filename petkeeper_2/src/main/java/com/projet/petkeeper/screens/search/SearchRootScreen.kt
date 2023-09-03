package com.projet.petkeeper.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRootScreen( name: String, onClick: () -> Unit

){
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
                    Text(text = "Search for a job...")
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, "search")
                }
            ) {

            }
        },
    ){  paddingValues ->

        // To do: display in column les annonces
        // + ensuite quand on clique sur l'annonce, on affiche la page de l'annonce.
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = name, fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchRootScreenPreview() {
    PetkeeperTheme {
        SearchRootScreen("search", onClick = { })
    }
}