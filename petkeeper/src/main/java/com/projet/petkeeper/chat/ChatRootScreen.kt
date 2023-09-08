package com.projet.petkeeper.chat

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projet.petkeeper.R
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.UserPair
import com.projet.petkeeper.data.userSamples
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import com.projet.petkeeper.utils.UserProfileImageIcon
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRootScreen(
    uiState: PetKeeperUIState,
    userData: UserData,
    onSearch: (String) -> Unit,
    onChatClick: (UserPair, UserData) -> Unit,
    fetchUserData: (String, (UserData) ->Unit) -> Unit
){
    var searchChatText by remember {
        mutableStateOf("")
    }
    var searchChatActive by remember {
        mutableStateOf(false)
    }
    val searchedItems: MutableSet<String> = remember {
        mutableSetOf()
    }

    Scaffold(
        modifier = Modifier
            .padding(15.dp),

        topBar = {

            SearchBar(
                query = searchChatText,
                onQueryChange = {
                    searchChatText = it
                },
                onSearch = {
                    onSearch(searchChatText)
                    searchedItems.add(searchChatText)
                    searchChatActive = false
                },
                active = searchChatActive,
                onActiveChange = {
                    searchChatActive = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search chats")
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, "Search icon")
                },
                trailingIcon = {
                    if(searchChatText.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable {
                                if(searchChatText.isNotEmpty()) {
                                    searchChatText = ""
                                } else {
                                    searchChatActive = false
                                }
                            },
                            painter = painterResource(id = R.drawable.baseline_history_24),
                            contentDescription = "Close icon"
                        )
                    }
                }
            ) {
                searchedItems.forEach {
                    Row(
                        modifier = Modifier
                            .clickable {
                                onSearch(it)

                            },
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh icon"
                        )
                        Text(text = it)
                    }
                }
            }
        },
    ){  paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.userPairList) {userPair ->

                Log.i("chat","this user pair: $userPair")

                ChatCard(
                    userData = userData,
                    userPair = userPair,
                    onChatClick = { otherUserData ->
                        onChatClick(userPair, otherUserData)
                    },
                    fetchUserData = {userId, fetch ->
                        fetchUserData(userId, fetch)
                    }
                )

            }
        }
    }
}

@Composable
fun ChatCard(
    userData: UserData,
    userPair: UserPair,
    onChatClick: (UserData) -> Unit,
    fetchUserData: (String, (UserData) ->Unit) -> Unit
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
            Log.v("userData", "other user : $otherUserData")
        }
    }

    Log.i("user", "$otherUserData")

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatRootScreenPreview() {
    PetkeeperTheme {
        ChatRootScreen(
            uiState = PetKeeperUIState(),
            userData = userSamples[0],
            {  },
            { _,_ -> },
            { _, _ -> }
        )
    }
}