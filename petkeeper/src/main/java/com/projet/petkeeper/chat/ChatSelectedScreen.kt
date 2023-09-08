package com.projet.petkeeper.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import com.projet.petkeeper.data.MessageData
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.UserPair
import com.projet.petkeeper.data.userSamples
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSelectedScreen (
    uiState: PetKeeperUIState,
    userData: UserData,
    onMessageSend: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    BackHandler {
        onBackClick()
    }

    val userIsUser1 = userData.userId == uiState.currentUserPair?.userId1

    Scaffold (
        modifier = Modifier
            .padding(15.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    uiState.currentChatter?.displayName?.let {
                        Text(
                            text = it
                        )
                    } ?: Text(
                        text = uiState.currentChatter?.displayName?:"Name not found"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back",
                        )
                    }
                }
            )
        }
    ) {paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),

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
                val messages = uiState.currentMessageList.reversed()
                items(
                    messages,
                ) { message ->
                    SingleMessage(
                        message = message,
                        userIsUser1 = userIsUser1
                    )
                }
            }
        }
    }
}

@Composable
fun SingleMessage(
    message: MessageData,
    userIsUser1: Boolean
) {
    val fromUser = message.sentByUser1 == userIsUser1

    val maxWidth = LocalConfiguration.current.screenWidthDp

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = if (fromUser) Arrangement.End  else  Arrangement.Start

        ) {
            Box(
                modifier = Modifier
                    .widthIn(0.dp, (maxWidth-100).dp)
                    .background(
                        color = if (fromUser) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        },
                        shape = if (fromUser) {
                            RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp, bottomStart = 8.dp)
                        } else {
                            RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp, bottomEnd = 8.dp)
                        }
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = message.messageData ?: "Error, message not found",
                    fontSize = 16.sp,
                    color = if (fromUser) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    }
                )
            }

        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PervewChatScreen(){
    PetkeeperTheme {
        ChatSelectedScreen(
            uiState = PetKeeperUIState(
                currentMessageList = listOf(
                    MessageData(
                        messageData = "Hello",
                        sentByUser1 = true,
                        timestamp = Timestamp(Date(0))
                    ),
                    MessageData(
                        messageData = "Hello",
                        sentByUser1 = false,
                        timestamp = Timestamp(Date(1))
                    ),
                    MessageData(
                        messageData = "How are yuo",
                        sentByUser1 = true,
                        timestamp = Timestamp(Date(2))
                    ),
                    MessageData(
                        messageData = "You*",
                        sentByUser1 = true,
                        timestamp = Timestamp(Date(3))
                    ),
                    MessageData(
                        messageData = "This is a realy long message" +
                                "so much so I had to write it on multiple" +
                                "lines, wow wow wow ...",
                        sentByUser1 = false,
                        timestamp = Timestamp(Date(4))
                    ),
                ),
                currentUserPair = UserPair(
                    id = "1",
                    userId1 = userSamples[0].userId,
                    userId2 = userSamples[1].userId
                )
            ),
            userData = userSamples[0],
            onMessageSend = {}
        ) {
            
        }
    }
}