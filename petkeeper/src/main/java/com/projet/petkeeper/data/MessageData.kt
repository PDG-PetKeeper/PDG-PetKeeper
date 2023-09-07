package com.projet.petkeeper.data

import com.google.firebase.Timestamp


data class MessageData(
    val messageData: String?,
    val sentByUser1: Boolean?,
    val timestamp: Timestamp?
)
