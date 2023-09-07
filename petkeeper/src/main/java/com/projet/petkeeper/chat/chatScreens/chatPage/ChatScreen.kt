package com.projet.petkeeper.chat.chatScreens.chatPage

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.projet.petkeeper.utils.Appbar
import com.projet.petkeeper.utils.rememberUserModel


@Composable
fun ChatScreen() {
    val chatPageViewModel: ChatPageViewModel = viewModel()

    // Fetch the user information (userModel) from Firebase Authentication
    val currentUser = Firebase.auth.currentUser
    val currentUserId = currentUser?.uid ?: ""
    val userModel = rememberUserModel(currentUserId)


    if (userModel != null) {
        // Create the Appbar with the user information
        Appbar(userData = userModel!!, action = {
            // TODO: Add navigation
        })

        // Pass the user information to the ChatPageView
        ChatView(chatPageViewModel = chatPageViewModel)
    } else {
        // Handle the case where user information is not available
        // display a loading indicator or an error message
    }
}


