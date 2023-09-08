package com.projet.petkeeper.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.projet.petkeeper.R
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.JobDataExample
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRootScreen(
    uiState: PetKeeperUIState,
    onSearch: (String) -> Unit,
    onJobClick: (JobData) -> Unit
){
    var searchChatText by remember {
        mutableStateOf("")
    }
    var searchChatActive by remember {
        mutableStateOf(false)
    }
    val searchedItems: MutableSet<String> = remember {
        mutableSetOf()
    }

    Scaffold(
        modifier = Modifier,

        topBar = {

            SearchBar(
                query = searchChatText,
                onQueryChange = {
                    searchChatText = it
                },
                onSearch = {
                    onSearch(searchChatText)
                    searchedItems.add(searchChatText)
                    searchChatActive = false
                },
                active = searchChatActive,
                onActiveChange = {
                    searchChatActive = it
                },
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search jobs")
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, "Search icon")
                },
                trailingIcon = {
                    if(searchChatText.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable {
                                if(searchChatText.isNotEmpty()) {
                                    searchChatText = ""
                                } else {
                                    searchChatActive = false
                                }
                            },
                            painter = painterResource(id = R.drawable.baseline_history_24),
                            contentDescription = "Close icon"
                        )
                    }
                }
            ) {
                searchedItems.forEach {
                    Row(
                        modifier = Modifier
                            .clickable {
                                onSearch(it)

                            },
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh icon"
                        )
                        Text(text = it)
                    }
                }
            }
        },
    ){  paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            val jobs = uiState.currentJobList
            items(jobs, key = {job -> job.id!!}){job ->
                SearchJobCard(jobData = job) {
                    onJobClick(job)
                }
            }

        }
    }
}

@Composable
fun SearchJobCard(
    jobData: JobData,
    onJobClick: () -> Unit
){
    val startDate: String = jobData.getDateString(true)
    val endDate: String = jobData.getDateString(false)

    ListItem(
        modifier = Modifier.clickable {onJobClick()},
        headlineContent = { jobData.title?.let { Text(it) } },
        supportingContent = { Text(text = "From $startDate to $endDate") },
        leadingContent = {
            AsyncImage(
                model = jobData.image,
                contentDescription = "First image of the job",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
        },
        trailingContent = { jobData.pay?.let { Text(text = it) } }
    )
    HorizontalDivider()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchRootScreenPreview() {
    PetkeeperTheme {
        SearchRootScreen(
            uiState = PetKeeperUIState(
                currentJobList = JobDataExample.jobDataExampleList
            ),
            {},
            {},
        )
    }
}