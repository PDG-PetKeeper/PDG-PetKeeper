package com.projet.petkeeper.sign_in

import com.projet.petkeeper.data.UserModel

data class SignInResult(
    val data: UserModel?,
    val errorMessage: String?
)


