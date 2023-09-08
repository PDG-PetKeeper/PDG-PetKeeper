package com.projet.petkeeper.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.projet.petkeeper.ui.PetKeeperUIState


/**
 * The screen that shows an add job advert form the dashboard.
 * @param uiState: the state of the application
 * @param onBackClick: the action to perform when the back button is clicked
 *                     -> goes back to the dashboard
 * @param onEditClick: the action to perform when the edit button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LookupJob(
    uiState: PetKeeperUIState,
    onBackClick: () -> Unit,
    // only when it is the user's job advert
    onEditClick: () -> Unit,
) {
    BackHandler {
        onBackClick()
    }
    Scaffold(
        topBar ={
            TopAppBar(
                title = {},
                navigationIcon = {
                    // go back
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back",
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color(0,0,0,0),
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onEditClick) {
                Icon(Icons.Filled.Edit, "Edit job advert")
            }
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            item{
                uiState.currentSelectedJob?.let { it.title?.let { it1 -> Text(text = it1) } }
            }
        }
    }
}

/*
the preview function for the LookupJob screen
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LookupJobScreenPreview() {
    PetkeeperTheme {
        LookupJob(
            PetKeeperUIState(
                currentJobList = JobDataExample.jobDataExampleList,
                currentSelectedJob = JobDataExample.jobDataExampleList[0]
            ),
            {},
            {}
        )
    }
}*/