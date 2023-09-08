package com.projet.petkeeper.data


data class  UserData (
    val userId: String? = null,
    val displayName: String? = null,
    //val lastName: String? = null,
    val photoURL: String? = null,
   // val email: String? = null,
)


val userSamples = listOf(
    UserData(
        userId = "poster 1",
        displayName = "Poster 1",
    ),
    UserData(
        userId = "1",
        displayName = "John",
    ),
    UserData(
        userId = "2",
        displayName = "Jane",
    ),
    UserData(
        userId = "3",
        displayName = "Bob",
    ),
    UserData(
        userId = "4",
        displayName = "Alice",
    ),
    UserData(
        userId = "5",
        displayName = "Tom",
    ),
)
