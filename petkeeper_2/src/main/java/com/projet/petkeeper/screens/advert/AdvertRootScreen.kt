package com.projet.petkeeper.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRootScreen(name: String, onClick: () -> Unit) {
    PetkeeperTheme {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text("DASHBOARD")
                        Color(0xFF000000)
                    })

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Spacer(modifier = Modifier.height(250.dp))
                    Button(
                        modifier = Modifier,
                        onClick = {}

                    )
                    {
                        Text("My adverts")
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    Button(
                        modifier = Modifier,
                        onClick = {}
                    )
                    {
                        Text("  My jobs ")
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                FloatingActionButton(
                    onClick = {onClick()},
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.End)

                ) {
                    Icon(Icons.Filled.Add, "Add a new advert")
                }
            }

        }
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardRootScreenPreview() {
    PetkeeperTheme {
        DashboardRootScreen("advert", onClick = {})
    }
}
