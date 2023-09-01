package com.projet.petkeeper.ui.chat.chatScreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projet.petkeeper.ui.chat.UserProfile
import com.projet.petkeeper.ui.chat.UserProfileData
import com.projet.petkeeper.ui.theme.PetkeeperTheme

@Composable
fun UserResultCard(userProfile: UserProfile) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User profile image
            Icon(
                painter = painterResource(id = UserProfileData.getUserProfileByName("User1")!!.profileImage),
                contentDescription = "Profile Image",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // User name
            Text(
                text = userProfile.name,
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}
@Composable
fun TopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchAction: () -> Unit
) {
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
                .background(MaterialTheme.colorScheme.secondary)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
            )

            //Spacer(modifier = Modifier.width(8.dp))

            // Search input field
            BasicTextField(
                value = searchQuery,
                onValueChange = { newQuery ->
                    onSearchQueryChange(newQuery)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSearchAction()
                    }
                ),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )

        }
    }
}
@Composable
fun SearchResults(userProfilesList: List<UserProfile>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(userProfilesList) { userProfile ->
            UserResultCard(userProfile)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar for searching
        TopBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { newQuery ->
                searchQuery = newQuery
                // Handle search query change
            },
            onSearchAction = {
                // Handle search action
            }
        )
        // Section for displaying search results
        SearchResults(userProfilesList = UserProfileData.userProfileList)
        //SearchResults(searchQuery = searchQuery)
    }
}

@Composable
fun SearchPage() {
    val userProfileList = UserProfileData.userProfileList
       // Call SearchResults with the userProfilesList
    SearchResults(userProfilesList = userProfileList)
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun PreviewSearchScreen() {
    PetkeeperTheme {

    }
}

