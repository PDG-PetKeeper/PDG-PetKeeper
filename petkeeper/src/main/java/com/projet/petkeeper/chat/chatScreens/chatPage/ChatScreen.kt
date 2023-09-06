package com.projet.petkeeper.chat.chatScreens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projet.petkeeper.chat.chatScreens.chatPage.ChatPageViewModel
import com.projet.petkeeper.chat.chatScreens.chatPage.ChatView



@Composable
fun ChatScreen() {
    val chatPageViewModel: ChatPageViewModel = viewModel() // Initialize your ViewModel if needed
    val currentUserModel = // Fetch or obtain the user information as UserModel

        // Pass the user information to the ChatView
        ChatView(chatPageViewModel = chatPageViewModel, userModel = currentUserModel)
}

