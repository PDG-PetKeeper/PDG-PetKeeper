package com.projet.petkeeper.data


data class  UserModel (
    val userId: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val profileImageUrl: String? = null,
    val email: String? = null,
)


val userSamples = listOf(
    UserModel(
        userId = "1",
        firstName = "John",
        lastName = "Doe",
        profileImageUrl = " ",
        email = "john@example.com"
    ),
    UserModel(
        userId = "2",
        firstName = "Jane",
        lastName = "Doe",
        profileImageUrl = " ",
        email = "jane@example.com"
    ),
    UserModel(
        userId = "3",
        firstName = "Bob",
        lastName = "Smith",
        profileImageUrl = " ",
        email = "Bob@example.com"
    ),
    UserModel(
        userId = "4",
        firstName = "Alice",
        lastName = "Smith",
        profileImageUrl = " ",
        email = "Alice@example.com"
    ),
    UserModel(
        userId = "5",
        firstName = "Tom",
        lastName = "Jones",
        profileImageUrl = " ",
        email = "tom@example.com"
    ),
)
