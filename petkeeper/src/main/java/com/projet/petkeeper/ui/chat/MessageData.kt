package com.projet.petkeeper.ui.chat

data class Message(val sender: String, val body: String, val isSent: Boolean)

object MessageData {
    val MessageList = listOf(
        Message("User", "Hello, how are you doing?this should be a long messge" +
                "Hello, how are you doing?this should be a long messge" +
                "Hello, how are you doing?this should be a long messge", true),
        Message("User", "Hello, how are you doing?", true),
        Message("User", "Hello, how are you doing?", true),
        Message("User", "Hello, how are you doing?", true)
    )
}