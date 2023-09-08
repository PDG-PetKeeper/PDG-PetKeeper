package com.projet.petkeeper.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.ui.theme.PetkeeperTheme

/**
 * Composable function representing the root screen of the user's profile.
 * This screen typically serves as the entry point to the user's profile and contain
 * various sections for displaying user information and actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRootScreen(

){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Profile",
                )
            })
        },
    ){  paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "This is the Profile root", fontSize = 30.sp)
        }
    }
}

/**
 * Composable function representing the root screen of the user's profile.
 * This screen typically displays user information and options related to their profile.
 *
 * @param userData The user's data, including their profile picture and display name.
 * @param onSignOut Callback function to sign the user out when the "Sign out" button is pressed.
 */
@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(userData?.photoURL != null) {
            AsyncImage(
                model = userData.photoURL,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(userData?.displayName != null) {
            Text(
                text = userData.displayName,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            onClick = {
                onSignOut()
            }
        ) {
            Text(text = "Sign out")
        }
    }
}
/**
 * Composable function for rendering a preview of the [ProfileRootScreen].
 * This is used for previewing the UI layout during development.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileRootScreenPreview() {
    PetkeeperTheme {
        ProfileRootScreen()
    }
}