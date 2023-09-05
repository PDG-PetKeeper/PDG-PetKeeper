package com.projet.petkeeper.chat.chatScreens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.projet.petkeeper.data.ChatMessage
import com.projet.petkeeper.data.UserModel
import com.projet.petkeeper.utils.fetchUserData
import com.projet.petkeeper.utils.sendChatMessage


@Composable
fun ChatScreen(chatViewModel: ChatViewModel, userModel: UserModel) {

    val messageList by rememberUpdatedState(chatViewModel.messages.value ?: emptyList())
    var newMessageText by remember { mutableStateOf("") }

    //mutable state to hold the image URI
    //var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    Column {
        // Top Bar with user profile and back arrow
        TopBar(userModel)

        // Chat messages
        Conversation(messageList, userModel)
        // Message input field and send button
        MessageInputField(
            messageText = newMessageText,
            onMessageTextChanged = { newMessageText = it },
            onSend = {
                // Send text message
                userModel.userId?.let { userId ->
                    sendChatMessage(
                        userId, newMessageText,
                        onSuccess = {
                            // Handle success
                        },
                        onFailure = {
                            // Handle failure,
                        }
                    )
                }
                newMessageText = ""
            }

        )
    }
}


@Composable
fun MessageInputField(
    messageText: String,
    onMessageTextChanged: (String) -> Unit,
    onSend: () -> Unit // Common callback for sending text or images
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(8.dp)
    ) {
        // Input field for typing messages
        BasicTextField(
            value = messageText,
            onValueChange = { newText ->
                onMessageTextChanged(newText)
            },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Common send button for both text and images
        IconButton(
            onClick = { onSend() }, // Invoke the common send callback
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send"
            )
        }
    }
}


@Composable
fun TopBar(userModel: UserModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Align children horizontally to start and end,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                    //TODO Handle back button click
                    }
            )
            Spacer(modifier = Modifier.weight(1f))

            // User name
            Text(
                text = userModel.firstName + " " + userModel.lastName,
                color = MaterialTheme.colorScheme.secondaryContainer,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
            // Spacer to push profile picture to the right
            Spacer(modifier = Modifier.weight(1f))

            // User profile image
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color.Transparent,
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary)
            ) {
                val profileImageUrl = userModel.profileImageUrl // Get the profile image URL
                val painter = rememberImagePainter(data = profileImageUrl)
                Image(
                    painter = painter,
                    contentDescription = "User Profile",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Composable
fun MessageBox(msg: ChatMessage, userModel: UserModel) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        val profileImageUrl = userModel.profileImageUrl // Get the profile image URL
        val painter = rememberImagePainter(data = profileImageUrl)
        Image(
            painter = painter,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var msgExpand by remember { mutableStateOf(false) }
        val surfaceColorChange: Color by animateColorAsState(
            if (msgExpand) MaterialTheme.colorScheme.tertiary
            else MaterialTheme.colorScheme.surface, label = ""
        )
        Column(modifier = Modifier.clickable { msgExpand = !msgExpand }) {
            Text(
                text = msg.message,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 1.dp,
                color = surfaceColorChange,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.message, // Check if msg.text is a String
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (msgExpand) Int.MAX_VALUE else 1
                )
            }
        }
    }
}


@Composable
fun Conversation(
    messageListState: List<ChatMessage>,
    userModel: UserModel
) {
    val messageList = messageListState.reversed()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        messageList.forEach { msg ->
            MessageBox(msg, userModel)
        }
    }
}


@Composable
fun UserChatScreen(userId: String) {
    var userModel by remember { mutableStateOf<UserModel?>(null) }

    // Fetch user data when the Composable is first displayed
    LaunchedEffect(userId) {
        fetchUserData(userId) { fetchedUserModel ->
            // Update the userModel state with the fetched data
            userModel = fetchedUserModel
        }
    }

    // Check if userModel is null or has data and display accordingly
    userModel?.let { user ->
        // Call the ChatScreen Composable with the fetched userModel
        ChatScreen(chatViewModel = ChatViewModel(), userModel = user)
    }
}

@Composable
fun MessageInputFieldPreview() {
    // MessageText variable with some predefined text for the preview
    var messageText by remember { mutableStateOf("Tired") }

    // Create a preview of the MessageInputField with the messageText variable
    MessageInputField(
        messageText = messageText,
        onMessageTextChanged = { messageText = it },
        onSend = {
            // TODO Handle send button click
        }

    )
}


@Composable
@Preview
fun PreviewMessageInputField() {
    MessageInputFieldPreview()
}


