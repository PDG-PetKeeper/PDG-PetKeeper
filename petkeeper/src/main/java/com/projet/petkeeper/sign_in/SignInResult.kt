package com.projet.petkeeper.sign_in

import com.projet.petkeeper.data.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)


