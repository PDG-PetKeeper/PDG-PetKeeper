package com.projet.petkeeper.sign_in

/**
 * This class represents the state of the sign-in screen.
 */
data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
