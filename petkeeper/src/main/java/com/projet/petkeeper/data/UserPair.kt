package com.projet.petkeeper.data

/**
 * Represents a pairing of two user IDs.
 *
 * @property id Unique identifier for the user pair.
 * @property userId1 The first user's ID.
 * @property userId2 The second user's ID.
 */
data class UserPair(
    val id: String? = null,
    val userId1: String? = null,
    val userId2: String? = null,
)

