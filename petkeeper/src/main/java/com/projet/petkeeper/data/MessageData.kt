package com.projet.petkeeper.data

import com.google.firebase.Timestamp

/**
 * Data class representing message data.
 *
 * @property messageData The message content.
 * @property sentByUser1 If true, the message was sent by user 1; otherwise, by user 2.
 * @property timestamp The timestamp when the message was sent.
 */
data class MessageData(
    val messageData: String?,
    val sentByUser1: Boolean?,
    val timestamp: Timestamp?
)
