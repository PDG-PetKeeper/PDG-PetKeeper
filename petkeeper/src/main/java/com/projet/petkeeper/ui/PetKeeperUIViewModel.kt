package com.projet.petkeeper.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Filter.equalTo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.MessageData
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.UserPair
import com.projet.petkeeper.utils.Constants
import com.projet.petkeeper.utils.fetchUserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date

open class PetKeeperUIViewModel() : ViewModel() {

    object ViewModel: PetKeeperUIViewModel()

    private val firestoreDB = Firebase.firestore

    private val _uiState = MutableStateFlow(PetKeeperUIState())
    val uiState: StateFlow<PetKeeperUIState> = _uiState

    lateinit var userData: UserData

    init {
        initializeUIState()
        dashboardInit()
        Log.e("Init", "viewModel was initializedg")
    }
    private fun initializeUIState()  = CoroutineScope(Dispatchers.IO).launch {
        _uiState.value = PetKeeperUIState()
    }
    fun searchInit() {
        val mutableJobList: MutableList<JobData> = mutableListOf()

        firestoreDB.collection("jobs")
            .whereNotEqualTo("posterId", userData.userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    mutableJobList.add(document.toObject())
                }
            }

        _uiState.update {
            it.copy(
                currentJobList = mutableJobList.toList()
            )
        }
    }

    fun chatInit()  = CoroutineScope(Dispatchers.IO).launch {
        val mutableUserPairsList: MutableList<UserPair> = mutableListOf()

        firestoreDB.collection("userPairs")
            .where(
                Filter.or(
                    equalTo("userId1", userData.userId),
                    equalTo("userId2", userData.userId)
                )
            )
            .get()
            .addOnSuccessListener {documents ->
                for (document in documents){
                    mutableUserPairsList.add(document.toObject())
                }
            }

        _uiState.update {
            it.copy(
                userPairList = mutableUserPairsList.toList()
            )
        }
    }
    fun dashboardInit() = CoroutineScope(Dispatchers.IO).launch {
        val mutableJobList: MutableList<JobData> = mutableListOf()


        Log.d(
            "StateJobList",
            "State List not empty : ${_uiState.value.currentJobList.isNotEmpty()}"
        )

        try {
            Log.i("JobUserId", "${userData.userId}")
            val querySnapshot = firestoreDB.collection("jobs")
                .whereEqualTo("poster", userData.userId)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                try {
                    val jobData = document.toObject<JobData>()
                    if (jobData != null) {
                        mutableJobList.add(jobData)
                    }
                } catch (e: Exception) {
                    Log.w("JobAdd", "Exception: $e")
                }
            }
            withContext(Dispatchers.Main){
                _uiState.update {
                    it.copy(
                        currentJobList = mutableJobList.toList()
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Exception: $e")
        }

        Log.d(
                "StateJobList",
        "State List not empty : ${_uiState.value.currentJobList.isNotEmpty()}"
        )
    }

    fun profileInit(){
        _uiState.update {
            it.copy(
                currentJobList = emptyList(),
                currentSelectedJob = null,
                currentUserPair = null,
                currentMessageList = emptyList()
            )
        }
    }

    // Send a chat message
    fun addMessage(
        messageString: String
    )  = CoroutineScope(Dispatchers.IO).launch {
        val isUser1 = _uiState.value.currentUserPair!!.userId1 == userData.userId
        val timestamp = Timestamp(Date())

        val messageDate = MessageData(
            messageData = messageString,
            sentByUser1 = isUser1,
            timestamp = timestamp
        )

        firestoreDB.collection("userPairs")
            .document(_uiState.value.currentUserPair!!.id!!)
            .collection("messages")
            .add(messageDate)
    }

    // Get chat messages
    fun updateCurrentMessages(userPair: UserPair)  = CoroutineScope(Dispatchers.IO).launch {
        val mutableMessageList: MutableList<MessageData> = mutableListOf()
        val otherUserId = if (userPair.userId1.equals(userData.userId)) {
            userPair.userId2!!
        } else {
            userPair.userId1!!
        }
        val otherUserData: UserData? = fetchUserData(otherUserId){}

        firestoreDB.collection("userPairs")
            .document(userPair.id!!)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }


                if (value != null) {
                    for (doc in value) {
                        mutableMessageList.add(doc.toObject())
                    }
                }
            }

        _uiState.update {
            it.copy(
                currentMessageList = mutableMessageList.toList().asReversed(),
                currentChatter = otherUserData
            )
        }
    }

    fun searchChats(queryString: String) {

        chatInit()

        val mutableUserPairList: MutableList<UserPair> = mutableListOf()
        val currentUserPairList = _uiState.value.userPairList

        currentUserPairList.forEach { userPair ->
            if (userPair.userId1.equals(userData.userId)
                && userPair.userId2.equals(queryString)){
                mutableUserPairList.add(userPair)
            } else if (userPair.userId2.equals(userData.userId)
                && userPair.userId1.equals(queryString)) {
                mutableUserPairList.add(userPair)
            }
        }

        _uiState.update {
            it.copy(
                userPairList = mutableUserPairList.toList()
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

        //uploadImage(jobData.image)
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
