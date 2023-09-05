package com.projet.petkeeper.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.JobDataExample
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRootScreen(
    uiState: PetKeeperUIState,
    onJobClick: (JobData) -> Unit
){
    PetkeeperTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = "Profile",
                    )
                })
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
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val jobs = uiState.currentJobList
                items(jobs, key = {job -> job.id}) { job ->
                    DashboardJobCard(
                        jobData = job,
                        onJobClick = {
                            onJobClick(job)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardJobCard(
    jobData: JobData,
    onJobClick: () -> Unit
){
    ListItem(
        modifier = Modifier.clickable {onJobClick()},
        headlineContent = { Text(jobData.title) },
        overlineContent = { Text("OVERLINE") },
        supportingContent = { Text("Secondary text") },
        leadingContent = {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Localized description",
            )
        },
        trailingContent = { Text("meta") }
    )
    HorizontalDivider()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardRootScreenPreview() {
    PetkeeperTheme {
        DashboardRootScreen(
            PetKeeperUIState(currentJobList = JobDataExample.jobDataExampleList)
        ){}
    }
}