package com.projet.petkeeper.chat.chatScreens.chatSearchs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projet.petkeeper.R
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.userSamples
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRootScreen(
    uiState: PetKeeperUIState,
    userData: UserData?,
    onSearch: (String) -> Unit,
    onChatClick: (JobData?) -> Unit
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
        modifier = Modifier
            .padding(15.dp),

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
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search chats")
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "This is the Chat root", fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatRootScreenPreview() {
    PetkeeperTheme {
        ChatRootScreen(
            uiState = PetKeeperUIState(),
            userData = userSamples[0],
            {},
            {}
        )
    }
}