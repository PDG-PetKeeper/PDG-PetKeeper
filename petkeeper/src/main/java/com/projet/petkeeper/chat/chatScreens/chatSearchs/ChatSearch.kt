package com.projet.petkeeper.chat.chatScreens.chatSearchs

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.data.ChatMessage
import com.projet.petkeeper.data.UserModel
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import com.projet.petkeeper.utils.ChatMessageItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSearchScreen(
    chatMessages: List<ChatMessage>,
    users: List<UserModel>
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                },
                onSearch = {// Filter chat messages based on the search query

                    val filteredChatMessages = mutableListOf<ChatMessage>()
                    for (chatMessage in chatMessages) {
                        val user = users.find { it.userId == chatMessage.senderId }
                        if (user != null && user.userName?.contains(searchQuery, ignoreCase = true) == true) {
                            filteredChatMessages.add(chatMessage)
                        }
                    }
                    chatMessages.clear()
                    chatMessages.addAll(filteredChatMessages)
                },
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search for messages by username")
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, "search")
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
            items(chatMessages) { chatMessage ->
                // Display each chat message item
                ChatMessageItem(chatMessage, userModel = users.find { it.userId == chatMessage.senderId }!!)
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


@Preview(showBackground = true)
@Composable
fun ChatSearchScreenPreview() {
    val chatMessages = listOf(
        ChatMessage(senderId = "1", receiverId = "2", message = "Hello"),
        ChatMessage(senderId = "2", receiverId = "1", message = "Hi there"),
        ChatMessage(senderId = "1", receiverId = "2", message = "How are you?"),
        // Add more chat messages here as needed
    )

    val users = listOf(
        UserModel(userId = "1", userName = "User1"),
        UserModel(userId = "2", userName = "User2"),
        // Add more users here as needed
    )
    PetkeeperTheme {
        ChatSearchScreen(
            chatMessages = chatMessages,
            users = users
        )
    }

}