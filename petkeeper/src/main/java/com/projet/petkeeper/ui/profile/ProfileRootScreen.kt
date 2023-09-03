package com.projet.petkeeper.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRootScreen(

){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Profile",
                )
            })
        },
    ){  paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "This is the Profile root", fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileRootScreenPreview() {
    PetkeeperTheme {
        ProfileRootScreen()
    }
}