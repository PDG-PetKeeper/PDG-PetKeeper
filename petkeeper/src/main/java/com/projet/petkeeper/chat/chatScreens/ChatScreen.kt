package com.projet.petkeeper.chat.chatScreens


import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.chat.Message
import com.projet.petkeeper.chat.MessageData
import com.projet.petkeeper.chat.UserProfile
import com.projet.petkeeper.chat.UserProfileData
import com.projet.petkeeper.ui.theme.PetkeeperTheme


@Composable
fun ChatScreen(messageList: List<Message>, userProfile: UserProfile) {
    Column {
        // Top Bar with user profile and back arrow
        TopBar(userProfile)

        // Chat messages
        Conversation(messageList, userProfile)
    }
}

@Composable
fun TopBar(userProfile: UserProfile) {
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
            // Back arrow (you can use your own icon)
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle back button click */ }
            )
            Spacer(modifier = Modifier.weight(1f))

            // User name
            Text(
                text = userProfile.name,
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
                color = Color.Transparent, // Transparent background
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary)
            ) {
                UserProfileData.getUserProfileByName("User1")
                    ?.let { painterResource(id = it.profileImage) }?.let {
                        Image(
                            painter = it,
                            contentDescription = "User Profile",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
            }
        }
    }
}

@Composable
fun MessageBox(msg: Message, userProfile: UserProfile) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            //TODO change picture to user profile picture
            painter = painterResource(userProfile.profileImage),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var msgExpand by remember { mutableStateOf(false) }
        val surfaceColorChange: Color by animateColorAsState(
            if (msgExpand) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface, label = ""
        )
        Column(modifier = Modifier.clickable { msgExpand = !msgExpand }) {
            Text(
                msg.sender,
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
                    msg.body,
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
fun Conversation(messageList: List<Message>, userProfile: UserProfile) {
    LazyColumn {
        items(messageList) { message ->
            MessageBox(message, userProfile)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun PreviewMessageBox() {
    PetkeeperTheme {
        UserProfileData.getUserProfileByName("User1")?.let {
            ChatScreen(
                messageList = MessageData.MessageList,
                userProfile = it
            )
        }

    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun PreviewLightMessageBox() {
    PetkeeperTheme {
        UserProfileData.getUserProfileByName("User1")?.let {
            ChatScreen(
                messageList = MessageData.MessageList,
                userProfile = it
            )
        }

    }
}
