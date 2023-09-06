package com.projet.petkeeper.data


data class  UserModel (
    val userId: String? = null,
    val userName: String? = null,
    //val lastName: String? = null,
    val profileImageUrl: String? = null,
   // val email: String? = null,
)


val userSamples = listOf(
    UserModel(
        userId = "1",
        userName = "John",
        profileImageUrl = " ",
    ),
    UserModel(
        userId = "2",
        userName = "Jane",
        profileImageUrl = " ",
    ),
    UserModel(
        userId = "3",
        userName = "Bob",
        profileImageUrl = " ",
    ),
    UserModel(
        userId = "4",
        userName = "Alice",
        profileImageUrl = " ",
    ),
    UserModel(
        userId = "5",
        userName = "Tom",
        profileImageUrl = " ",
    ),
)
