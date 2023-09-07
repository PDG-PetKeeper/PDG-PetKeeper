package com.projet.petkeeper.chat.chatScreens.chatPage


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.PetKeeperUIViewModel
import com.projet.petkeeper.utils.Constants
import com.projet.petkeeper.utils.SendButton
import com.projet.petkeeper.utils.SingleMessage
import com.projet.petkeeper.utils.TextFormField

// list of chat messages sent by user and option to send a message



@Composable
fun ChatView(uiState: PetKeeperUIState,
             petKeeperUIViewModel: PetKeeperUIViewModel = viewModel()
) {
    // column to show the conversation
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.85f, fill = true),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            reverseLayout = true
        ) {
            items(uiState.chatList ) { message ->
                val isCurrentUser = message[Constants.IS_CURRENT_USER] as Boolean

                SingleMessage(
                    message = message[Constants.MESSAGE].toString(),
                    isCurrentUser = isCurrentUser,
                )
            }
        }
        uiState.chatMessage?.let {
            TextFormField(
                value = it.message,
                onValueChange = {
                    petKeeperUIViewModel.updateMessage(it)
                },
                label = "Type Your Message",
                keyboardType = KeyboardType.Text,
                visualTransformation = VisualTransformation.None
            ) {
                SendButton {
                    petKeeperUIViewModel.addMessage()
                }
            }
        }

    }
}
