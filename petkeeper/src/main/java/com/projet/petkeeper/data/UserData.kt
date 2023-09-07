package com.projet.petkeeper.data


data class  UserData (
    val userId: String? = null,
    val userName: String? = null,
    //val lastName: String? = null,
    val profileImageUrl: String? = null,
   // val email: String? = null,
)


val userSamples = listOf(
    UserData(
        userId = "poster 1",
        userName = "Poster 1",
    ),
    UserData(
        userId = "1",
        userName = "John",
        profileImageUrl = " ",
    ),
    UserData(
        userId = "2",
        userName = "Jane",
        profileImageUrl = " ",
    ),
    UserData(
        userId = "3",
        userName = "Bob",
        profileImageUrl = " ",
    ),
    UserData(
        userId = "4",
        userName = "Alice",
        profileImageUrl = " ",
    ),
    UserData(
        userId = "5",
        userName = "Tom",
        profileImageUrl = " ",
    ),
)
