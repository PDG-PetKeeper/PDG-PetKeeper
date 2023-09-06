package com.projet.petkeeper.data

import com.google.firebase.Timestamp


data class ChatMessage(
    var senderId: String,
    var receiverId: String,
    var message: String,
    var timestamp: Timestamp = Timestamp.now()
)






