package com.projet.petkeeper.ui

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.projet.petkeeper.data.ChatMessage
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetKeeperUIViewModel(val userData: UserData, private val coroutineScope: CoroutineScope) : ViewModel() {

    var searchQuery = { mutableStateOf("") }
    val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("messages")

    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val usersCollection: CollectionReference = firestore.collection("users")

    val chatMessages = mutableListOf<ChatMessage>()

    private val firestoreDB = Firebase.firestore

    private val _uiState = MutableStateFlow(PetKeeperUIState())
    val uiState: StateFlow<PetKeeperUIState> = _uiState

    // Chat-related properties
    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private var _messages = MutableLiveData(emptyList<Map<String, Any>>().toMutableList())
    val messages: LiveData<MutableList<Map<String, Any>>> = _messages

    val users = mutableListOf<UserData>()
    val currentUser = Firebase.auth.currentUser
    val currentUserId = currentUser?.uid ?: ""

    init {
        initializeUIState()
    }

    private fun getUsers() {
        usersCollection.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot: QuerySnapshot? = task.result
                    querySnapshot?.documents?.forEach { document: DocumentSnapshot ->
                        val user = document.toObject(UserData::class.java)
                        user?.let { users.add(it) }
                    }

                }
            }
    }

    private fun initializeUIState() {
        _uiState.value = PetKeeperUIState()
        dashboardInit()
    }
    fun searchInit() {
        val mutableJobList: MutableList<JobData> = mutableListOf()

        coroutineScope.launch {
            firestoreDB.collection("jobs")
                .whereNotEqualTo("posterId", userData.userId)
                .get()
                .addOnSuccessListener {documents ->
                    for (document in documents){
                        mutableJobList.add(document.toObject())
                    }
                }
        }

        _uiState.update {
            it.copy(
                currentJobList = mutableJobList.toList()
            )
        }
    // Chat-related methods

    // Update the message value as the user types
    fun updateMessage(message: String) {
        _message.value = message
    }

    // Send a chat message
    fun addMessage() {
        val message: String = _message.value ?: throw IllegalArgumentException("message is empty")
        if (message.isNotEmpty()) {
            if (currentUser != null) {
                val chatMessage = ChatMessage(
                    senderId = currentUser.uid,
                    receiverId = uiState.value.currentChatReceiver?.userId!!, // Replace with the recipient's user ID
                    message = message,
                    timestamp = Timestamp.now()
                )
                Firebase.firestore.collection(Constants.CHAT_MESSAGES)
                    .add(chatMessage)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "Message added with ID: ${documentReference.id}")
                        _message.value = ""
                    }
                    .addOnFailureListener { e ->
                        Log.e(ContentValues.TAG, "Error adding message", e)
                        // Handle the error here
                    }
            } else {
                Log.e(ContentValues.TAG, "Error adding message")
            }
        }
    }

    // Get chat messages
    fun getMessages() {
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

    // Update the list after getting chat details from Firestore
    private fun updateMessages(list: MutableList<Map<String, Any>>) {
        _messages.value = list.asReversed()
    }

    fun searchChats(queryString: String) {
        // Filter chat messages based on the search query

        // Fetch chat messages from Firebase
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatMessages.clear()
                for (data in snapshot.children) {
                    val chatMessage = data.getValue(ChatMessage::class.java)
                    chatMessage?.let { chatMessages.add(it) }
                }

                // Update the UI with the new chatMessages list
                // You may want to call your filtering logic here as well
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        val filteredChatMessages = mutableListOf<ChatMessage>()
        for (chatMessage in uiState.value.chatList) {
            val user = users.find { it.userId == chatMessage.senderId }
            if ((user != null) && (user.userName?.contains(
                    queryString, // Change searchQuery to queryString here
                    ignoreCase = true
                ) == true)
            ) {
                filteredChatMessages.add(chatMessage)
            }
        }
        chatMessages.clear()
        chatMessages.addAll(filteredChatMessages)
    }


    fun searchInit() {
        var mutableJobList: MutableList<JobData>
        firestoreDB.collection("")
    }

    fun chatInit() {
        val mutableChatList: MutableList<Int> = mutableListOf()

        coroutineScope.launch {
            firestoreDB.collection("messages")
                .whereEqualTo("posterId", userData.userId)
                .get()
                .addOnSuccessListener {documents ->
                    for (document in documents){
                        mutableChatList.add(document.toObject())
                    }
                }
        }

        _uiState.update {
            it.copy(
                chatList = mutableChatList.toList()
            )
        }
    }

    fun dashboardInit() {
        val mutableJobList: MutableList<JobData> = mutableListOf()

        coroutineScope.launch {
            firestoreDB.collection("jobs")
                .whereEqualTo("posterId", userData.userId)
                .get()
                .addOnSuccessListener {documents ->
                    for (document in documents){
                        mutableJobList.add(document.toObject())
                    }
                }
        }

        _uiState.update {
            it.copy(
                currentJobList = mutableJobList.toList()
            )
        }
    }

    fun profileInit(){
        _uiState.update {
            it.copy(
                currentJobList = emptyList(),
                currentSelectedJob = null,
                currentChat = null,
            )
        }
    }



    fun changeNavBarCurrentIndex(index: Int){
        _uiState.update {
            it.copy(
                currentNavBarItemIndex = index
            )
        }
    }

    fun showNavBar() {
        _uiState.update {
            it.copy(
                mustShowNavBar = true
            )
        }
    }

    fun hideNavBar() {
        _uiState.update {
            it.copy(
                mustShowNavBar = false
            )
        }
    }

    fun updateSelectedJob(jobData: JobData?) {
        _uiState.update {
            it.copy(
                currentSelectedJob = jobData
            )
        }
    }

    fun searchJobs(searchString: String) {

    }


    fun addJob(jobData: JobData) {

        uploadImage(jobData.image)
    }

    private fun uploadImage(image: Uri?) {
        try {
            // creating a storage reference
            // var


        } catch (e: Exception) {
            println("Error uploading image")
        }


    }


}
