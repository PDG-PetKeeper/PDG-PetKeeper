package com.projet.petkeeper.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.JobDataExample
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme


/**
 * The root screen of the dashboard.
 * @ param uiState: the state of the application
 * @ param onJobClick: the action to perform when a job advert is clicked
 *                      -> goes to the job advert screen
 * @ param onAddClick: the action to perform when the add button is clicked
 *                      -> goes to the add job screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRootScreen(
    uiState: PetKeeperUIState,
    onJobClick: (JobData) -> Unit,
    onAddClick: () -> Unit
){
    PetkeeperTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Dashboard",
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onAddClick() }
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
                items(jobs, key = {job -> job.id!! }) { job ->
                    DashboardJobCard(
                        jobData = job,
                        onJobClick = {
                            onJobClick(job)
                        }
                    )
                }
                item{
                    HorizontalDivider()
                }
            }
        }
    }
}

/**
 * A card that displays a job advert.
 * @ param jobData: the job advert to display
 * @ param onJobClick: the action to perform when the card is clicked
 */
@Composable
fun DashboardJobCard(
    jobData: JobData,
    onJobClick: () -> Unit
){
    val startDate: String = jobData.getDateString(true)
    val endDate: String = jobData.getDateString(false)

    HorizontalDivider()
    ListItem(
        modifier = Modifier.clickable {onJobClick()},
        headlineContent = { jobData.title?.let { Text(it) } },
        supportingContent = { Text(text = "From $startDate to $endDate") },
        leadingContent = {
            AsyncImage(
                model = jobData.image,
                contentDescription = "image of the job",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
        },
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardRootScreenPreview() {
    PetkeeperTheme {
        DashboardRootScreen(
            PetKeeperUIState(
                currentJobList = JobDataExample.jobDataExampleList
            ),
            {},
            {},
        )
    }
}