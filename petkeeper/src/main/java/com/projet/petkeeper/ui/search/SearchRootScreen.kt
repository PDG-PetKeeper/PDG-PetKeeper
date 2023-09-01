package com.projet.petkeeper.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRootScreen(

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
                    Text(text = "Search for a job")
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, "search")
                }
            ) {

            }
        },
    ){  paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "This is the Search root", fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchRootScreenPreview() {
    PetkeeperTheme {
        SearchRootScreen()
    }
}