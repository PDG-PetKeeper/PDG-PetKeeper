package com.projet.petkeeper.sign_in

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projet.petkeeper.R
import com.projet.petkeeper.ui.theme.PetkeeperTheme

/**
 * The sign in screen of the PetKeeper app.
 * @param state: the state of the sign in screen
 * @param onSignInClick: the action to perform when the sign in button is clicked
 */
@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)){


            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_petkeeper_foreground),
                contentDescription = "logo",
                modifier = Modifier
                    .size(250.dp)
                    .padding(0.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Welcome to PetKeeper",
                modifier = Modifier
                    .padding(0.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 32.sp

            )

            Spacer(modifier = Modifier.height(1.dp))
        }

        Spacer(modifier = Modifier.height(200.dp))
        Button(
            onClick = {
                onSignInClick()
            }
        ) {
            /*
            //TODO add the google logo
            Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "google logo"
            )*/


            Text(text = "Sign in with Google")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewSignInScreen() {
    PetkeeperTheme {
        SignInScreen(SignInState(false), {})
    }
}
