package com.projet.petkeeper.data

import com.google.firebase.Timestamp

/**
 * Represents a chat message with sender and receiver information.
 *
 * @property senderId The ID of the sender of the message.
 * @property receiverId The ID of the receiver of the message.
 * @property message The content of the message.
 * @property timestamp The timestamp when the message was sent (default is the current timestamp).
 */
data class ChatMessage(
    var senderId: String,
    var receiverId: String,
    var message: String,
    var timestamp: Timestamp = Timestamp.now()
) {
    /**
     * Custom operator function to get the message content.
     *
     * @param message The message content.
     * @return The message content as an [Any] type.
     */
    operator fun get(message: String): Any {
        return message

    }
}






