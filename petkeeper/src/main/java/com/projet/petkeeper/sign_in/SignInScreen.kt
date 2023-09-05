package com.projet.petkeeper.sign_in


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.R


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
            /*Button(onClick = onClick) {
                Text(text = "Bypass login")
            }*/

            Column(modifier = Modifier
                .fillMaxSize().align(Alignment.Center)){


                Icon(
                    painter = painterResource(id = R.drawable.petkeeper_playstore),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(text = "Welcome to PetKeeper",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(200.dp))
            }


            Button(onClick = { onSignInClick()
            }) {
                /*Icon(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = "google logo"
                )*/


                Text(text = "Sign in with Google")
            }
        }

}