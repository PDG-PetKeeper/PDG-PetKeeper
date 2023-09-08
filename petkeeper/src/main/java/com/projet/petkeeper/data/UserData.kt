package com.projet.petkeeper.data

/**
 * Data class representing user data.
 *
 * @property userId The ID of the user.
 * @property displayName The display name of the user.
 * @property photoURL The photo URL of the user.
 */
data class  UserData (
    val userId: String? = null,
    val displayName: String? = null,
    val photoURL: String? = null,
)

/**
 * Object containing a list of example of userSamples for testing or demonstration purposes.
 */
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
