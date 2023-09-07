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
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.projet.petkeeper.chat.chatScreens.chatPage.ChatPageViewModel
import com.projet.petkeeper.data.ChatMessage
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.utils.ChatMessageItem



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSearchScreen(chatPageViewModel: ChatPageViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("messages")

    val chatMessages = mutableListOf<ChatMessage>()

    val users = mutableListOf<UserData>()
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val usersCollection: CollectionReference = firestore.collection("users")

    usersCollection.get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot: QuerySnapshot? = task.result
                querySnapshot?.documents?.forEach { document: DocumentSnapshot ->
                    val user = document.toObject(UserData::class.java)
                    user?.let { users.add(it) }
                }

            }
        }

    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                },
                onSearch = {// Filter chat messages based on the search query

                    // Fetch chat messages from Firebase

                    databaseReference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            chatMessages.clear()
                            for (data in snapshot.children) {
                                val chatMessage = data.getValue(ChatMessage::class.java)
                                chatMessage?.let { chatMessages.add(it) }
                            }

                            // Update the UI with the new chatMessages list
                            // You may want to call your filtering logic here as well
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle database error
                        }
                    })

                    val filteredChatMessages = mutableListOf<ChatMessage>()
                    for (chatMessage in chatMessages) {
                        val user = users.find { it.userId == chatMessage.senderId }
                        if (user != null && user.userName?.contains(
                                searchQuery,
                                ignoreCase = true
                            ) == true
                        ) {
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
                ChatMessageItem(
                    chatMessage,
                    userData = users.find { it.userId == chatMessage.senderId }!!,
                    onChatMessageClick = {
                        chatPageViewModel.getMessages()
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
