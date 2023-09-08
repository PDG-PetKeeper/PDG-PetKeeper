package com.projet.petkeeper.sign_in

import com.projet.petkeeper.data.UserData

/**
 * This class represents the result of the sign-in process.
 * @param data The user data if the sign-in was successful.
 * @param errorMessage The error message if the sign-in failed.
 */
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)


