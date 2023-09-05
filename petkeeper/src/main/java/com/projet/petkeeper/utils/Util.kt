package com.projet.petkeeper.utils

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.projet.petkeeper.data.ChatMessage
import com.projet.petkeeper.data.UserModel


// Store user data in Firestore when the user registers or logs in
// used for a sample since sign in is not finished

fun registerUsers(userSamples: List<UserModel>) {
    userSamples.forEach { userModel ->
        userModel.userId?.let {
            userModel.firstName?.let { it1 ->
                userModel.lastName?.let { it2 ->
                    userModel.email?.let { it3 ->
                        registerUser(
                            userId = it,
                            firstName = it1,
                            lastName = it2,
                            profileImageUrl = userModel.profileImageUrl,
                            email = it3,

                            )
                    }
                }
            }
        }
    }
}
fun registerUser(
    userId: String,
    firstName: String,
    lastName: String,
    email: String,
    profileImageUrl: String?
) {
    storeUserDataInFirestore(userId, firstName, lastName, profileImageUrl, email,
        onSuccess = {
            // User data stored successfully
        },
        onFailure = { error ->
            // Handle the error
        }
    )
}
fun storeUserDataInFirestore(
    userId: String,
    firstName: String,
    lastName: String,
    profileImageUrl: String?,
    email: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    // Create a UserModel object
    val userModel = UserModel(userId,firstName,lastName ,profileImageUrl, email)

    // Store the user data in Firestore
    val db = FirebaseFirestore.getInstance()
    val usersCollection = db.collection("users") // Create a "users" collection in Firestore

    // Use the user's id as the document ID
    usersCollection.document(userId).set(userModel)
        .addOnSuccessListener {
            // User data successfully stored in Firestore
            onSuccess()
        }
        .addOnFailureListener { e ->
            // Handle the error
            onFailure("Error storing user data: $e")
        }

}
// Fetch user data from Firestore based on the user's id
fun fetchUserData(userId: String, onUserModelFetched: (UserModel) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val usersCollection = db.collection("users")

    usersCollection.document(userId)
        .get()
        .addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val userModel = documentSnapshot.toObject(UserModel::class.java)
                if (userModel != null) {
                    // User data retrieved successfully
                    onUserModelFetched(userModel)
                } else {
                    // TODO Handle the case where userModel is null
                }
            } else {
                // TODO Handle the case where the document does not exist for the given userId
            }
        }
        .addOnFailureListener { exception ->
            // TODO Handle the error
        }
}

fun fetchChatListFromFirestore(onSuccess: (List<ChatMessage>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val chatListCollection = db.collection("chats") // Replace with the actual Firestore collection name for chats

    chatListCollection.get()
        .addOnSuccessListener { querySnapshot ->
            val chatDataList = mutableListOf<ChatMessage>()
            for (document in querySnapshot.documents) {
                val chatId = document.id
                val senderId = document.getString("senderId") ?: ""
                val recipientId = document.getString("recipientId") ?: ""
                val message = document.getString("message") ?: ""
                val timestamp = document.getTimestamp("timestamp")
                val chatMessage =
                    timestamp?.let { ChatMessage(senderId, message,timestamp= it) }
                if (chatMessage != null) {
                    chatDataList.add(chatMessage)
                }
            }
            onSuccess(chatDataList)
        }
        .addOnFailureListener { e ->
            Log.e(ContentValues.TAG, "Error fetching chat list: $e")
        }
}

fun sendChatMessage(
    senderId: String,
   // recipientId: String,
    message: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val chatCollection = db.collection("chats") // Replace with your Firestore collection name

    val chatMessage = ChatMessage(senderId, message)

    chatCollection.add(chatMessage)
        .addOnSuccessListener {
            // Chat message successfully stored in Firestore
            onSuccess()
        }
        .addOnFailureListener { e ->
            // Handle the error
            onFailure("Error sending chat message: $e")
        }
}


fun fetchChatMessages(
    onSuccess: (List<ChatMessage>) -> Unit,
    onFailure: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val chatCollection = db.collection("chats")

    chatCollection
        .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
        .limit(50) // Limit the number of messages fetched
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Handle errors here
                return@addSnapshotListener
            }

            val messagesList = mutableListOf<ChatMessage>()
            for (document in snapshot?.documents ?: emptyList()) {
                val message = document.toObject(ChatMessage::class.java)
                message?.let { messagesList.add(it) }
            }
            onSuccess(messagesList)
        }
}
