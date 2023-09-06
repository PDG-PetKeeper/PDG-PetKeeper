package com.projet.petkeeper.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.projet.petkeeper.data.UserModel

/**
 * Set different icons/views which will be used throughout the application.
 * Used to increase the code usability.
 */

@Composable
fun Title(title: String) {
    Text(
        text = title,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxHeight(0.5f)
    )
}

@Composable
fun Buttons(title: String, onClick: () -> Unit, backgroundColor: Color) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0),
    ) {
        Title(title = title)
    }
}

@Composable
fun SendButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Send Button"
        )
    }
}

@Composable
fun Appbar(action: () -> Unit, userModel: UserModel) {
    TopAppBar(
        title = {
            AppbarContent(action = action, userModel = userModel)
        },
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape
            ),
        navigationIcon = null
    )
}

@Composable
fun AppbarContent(action: () -> Unit, userModel: UserModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = action
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back button"
            )
        }
        Text(
            text = userModel.firstName + " " + userModel.lastName,
            textAlign = TextAlign.Center
        )
        UserProfileImageIcon(userModel = userModel)
    }
}

@Composable
fun UserProfileImageIcon(userModel: UserModel) {
    Surface(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(
            1.5.dp,
            MaterialTheme.colorScheme.secondary
        )
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

@Composable
fun TextFormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    sendButton: @Composable () -> Unit // Customizable send button
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(label)
            },
            maxLines = 1,
            modifier = Modifier
                .weight(1f) // Take up remaining horizontal space
                .padding(horizontal = 20.dp, vertical = 5.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            singleLine = true,
            visualTransformation = visualTransformation,
        )
        sendButton() // Include the custom send button
    }
}


@Composable
fun SingleMessage(message: String, isCurrentUser: Boolean) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (isCurrentUser) MaterialTheme.colorScheme.primary else Color.White,
    ) {
        Text(
            text = message,
            modifier = Modifier
                .padding(16.dp),
            color = if (!isCurrentUser) MaterialTheme.colorScheme.primary else Color.White,
            textAlign =
            if (isCurrentUser)
                TextAlign.End
            else
                TextAlign.Start,
        )
    }

}


@Composable
fun rememberUserModel(currentUserId: String): UserModel? {
    var userModel by remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(Unit) {
        fetchUserData(currentUserId) { userData ->
            userModel = userData
        }
    }

    return userModel
}
