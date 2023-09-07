package com.projet.petkeeper.chat.chatScreens.chatSearchs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.R
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.userSamples
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import com.projet.petkeeper.utils.ChatMessageItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSearchScreen(
    uiState: PetKeeperUIState,
    userData: UserData,
    onSearch: (String) -> Unit,
    onChatClick: (UserData) -> Unit
) {
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
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "chats")
                    },
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "search chats")
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

                }

        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.chatList) { chatMessage ->
                // Display each chat message item
                ChatMessageItem(
                    uiState = uiState,
                    chatMessage = chatMessage,
                    userData = uiState.users.find { it.userId == chatMessage.senderId }!!,
                    onChatMessageClick = {


                    }
                )
            }
        }
    }
}


private fun <E> List<E>.addAll(filteredChatMessages: MutableList<E>) {
    this.toMutableList().addAll(filteredChatMessages)

}

private fun <E> List<E>.clear() {
    this.toMutableList().clear()
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatSearchScreen() {
    PetkeeperTheme {
        ChatSearchScreen(
            uiState = PetKeeperUIState(),
            userData = userSamples[0],
            {},
            {}
        )
    }
}