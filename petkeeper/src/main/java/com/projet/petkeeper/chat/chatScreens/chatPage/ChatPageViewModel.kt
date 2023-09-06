package com.projet.petkeeper.chat.chatScreens.chatPage


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projet.petkeeper.data.ChatMessage
import com.projet.petkeeper.utils.Constants


class ChatPageViewModel(private val currentUserId: String,private val receiverUserId: String) : ViewModel() {
    init {
        getMessages()
    }

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private var _messages = MutableLiveData(emptyList<Map<String, Any>>().toMutableList())
    val messages: LiveData<MutableList<Map<String, Any>>> = _messages

    // Update the message value as user types
    fun updateMessage(message: String) {
        _message.value = message
    }

    // send message
    @SuppressLint("SuspiciousIndentation")
    fun addMessage() {
        val message: String = _message.value ?: throw IllegalArgumentException("message is empty")
        if (message.isNotEmpty()) {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
            val chatMessage = ChatMessage(
                senderId = currentUser.uid,
                receiverId = receiverUserId, // Replace with the recipient's user ID
                message = message,
                timestamp = Timestamp.now()
            )
                Firebase.firestore.collection(Constants.CHAT_MESSAGES)
                    .add(chatMessage)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "Message added with ID: ${documentReference.id}")
                        _message.value = ""
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error adding message", e)
                        // Handle the error here
                    }
            }else{
                Log.e(TAG, "Error adding message")
            }

        }
    }

    //Get the messages

    private fun getMessages() {
        Firebase.firestore.collection(Constants.CHAT_MESSAGES)
            .orderBy(Constants.SENT_ON)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val list = emptyList<Map<String, Any>>().toMutableList()

                if (value != null) {
                    for (doc in value) {
                        val data = doc.data
                        data[Constants.IS_CURRENT_USER] =
                            currentUserId == data[Constants.SENT_BY].toString()

                        list.add(data)
                    }
                }

                updateMessages(list)
            }
    }

    //Update the list after getting the details from firestore

    private fun updateMessages(list: MutableList<Map<String, Any>>) {
        _messages.value = list.asReversed()
    }
}