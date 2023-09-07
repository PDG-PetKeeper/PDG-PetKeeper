package com.projet.petkeeper.chat.chatScreens.chatPage

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.userSamples
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import com.projet.petkeeper.utils.Appbar


@Composable
fun ChatScreen(
    userData: UserData,
    uiState: PetKeeperUIState,
) {
    if (userData != null) {
        // Create the Appbar with the user information
        Appbar(userData = userData!!, action = {
            // TODO: Add navigation
        })

        // Pass the user information to the ChatPageView
        ChatView(uiState)
    } else {
        // Handle the case where user information is not available
        // display a loading indicator or an error message
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    PetkeeperTheme {
        ChatScreen(
            userData = userSamples[0],
            uiState = PetKeeperUIState()
        )
    }
}
