package com.projet.petkeeper.chat

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.UserPair
import com.projet.petkeeper.data.userSamples
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import kotlinx.coroutines.runBlocking


/**
 * This is the root screen for the chat feature of the PetKeeper app
 * @param uiState The current UI state of the app.
 * @param userData The user data for the logged-in user.
 * @param onSearch A callback function to perform a chat search based on the provided query.
 * @param onChatClick A callback function to handle a chat item click.
 * @param fetchUserData A function to fetch user data for a given user ID.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRootScreen(
    uiState: PetKeeperUIState,
    userData: UserData,
    onChatClick: (UserPair, UserData) -> Unit,
    fetchUserData: (String, (UserData) -> Unit) -> Unit
){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Chat",
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.userPairList) { userPair ->

                Log.i("chat", "this user pair: $userPair")

                ChatCard(
                    userData = userData,
                    userPair = userPair,
                    onChatClick = { otherUserData ->
                        onChatClick(userPair, otherUserData)
                    },
                    fetchUserData = { userId, fetch ->
                        fetchUserData(userId, fetch)
                    }
                )

            }
        }
    }
}
/**
 * A composable function to display a chat card with user information.
 *
 * @param userData The user data for the logged-in user.
 * @param userPair The user pair associated with the chat.
 * @param onChatClick A callback function to handle a chat card click.
 * @param fetchUserData A function to fetch user data for a given user ID.
 */
@Composable
fun ChatCard(
    userData: UserData,
    userPair: UserPair,
    onChatClick: (UserData) -> Unit,
    fetchUserData: (String, (UserData) -> Unit) -> Unit
) {
    val otherUserId: String? = if (userPair.userId1.equals(userData.userId)) {
        userPair.userId2
    } else {
        userPair.userId1
    }

    var otherUserData by remember { mutableStateOf<UserData?>(null) }


    runBlocking{
        fetchUserData(otherUserId!!) { newUserData ->
            otherUserData = newUserData
        }
    }


    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable { otherUserData?.let { onChatClick(it) } },
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserProfileImageIcon(userData = otherUserData)

        Text(
            text = otherUserData?.displayName?: "User name not found",
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
        )
    }

}


@Composable
fun UserProfileImageIcon(userData: UserData?) {
    Surface(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(
            1.5.dp,
            MaterialTheme.colorScheme.secondary
        )
    ) {
        val profileImageUrl = userData?.photoURL // Get the profile image URL
        val painter = rememberAsyncImagePainter(model = profileImageUrl)
        Image(
            painter = painter,
            contentDescription = "User Profile",
            modifier = Modifier.fillMaxSize()
        )
    }
}


/**
 * Preview function to display a preview of the ChatRootScreen composable.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatRootScreenPreview() {
    PetkeeperTheme {
        ChatRootScreen(
            uiState = PetKeeperUIState(),
            userData = userSamples[0],
            { _, _ -> },
            { _, _ -> }
        )
    }
}