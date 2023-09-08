package com.projet.petkeeper.ui

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
    }
    private fun initializeUIState(){
        _uiState.value = PetKeeperUIState()
        dashboardInit()
    }
    fun fetchUserDataFromUserId(userId: String, fetcher: (UserData) -> Unit) =  CoroutineScope(Dispatchers.IO).launch {

            try {
                val querySnapshot = firestoreDB.collection("users")
                    .document(userId)
                    .get()
                    .await()

                val userData = querySnapshot.toObject<UserData>()

                if (userData != null) {
                    withContext(Dispatchers.Main) {
                        fetcher(userData)
                    }
                } else {
                    Log.e("UserData fetch","something went wrong")
                }

            } catch (e: Exception) {
                Log.e("Firestore", "Exception: $e")
            }
    }

    fun searchInit() = CoroutineScope(Dispatchers.IO).launch {
        val mutableJobList: MutableList<JobData> = mutableListOf()

        try {
            Log.i("JobUserId", "${userData.userId}")
            val querySnapshot = firestoreDB.collection("jobs")
                .whereNotEqualTo("poster", userData.userId)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                try {
                    val jobData = document.toObject<JobData>()
                    if (jobData != null) {
                        mutableJobList.add(jobData)
                    } else {
                        Log.e("searchInit fetch","something went wrong")
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
    }

    fun chatInit() = CoroutineScope(Dispatchers.IO).launch {
        val mutableUserPairsList: MutableList<UserPair> = mutableListOf()

        try {
            val querySnapshot = firestoreDB.collection("userPairs")
                .where(
                    Filter.or(
                        equalTo("userId1", userData.userId),
                        equalTo("userId2", userData.userId)
                    )
                )
                .get()
                .await()

            for (document in querySnapshot.documents) {
                try {
                    val userPair = document.toObject<UserPair>()
                    if (userPair != null) {
                        mutableUserPairsList.add(userPair)
                        Log.d("modelPairAdd", "Added user pair : ${userPair.toString()}")
                    }
                } catch (e: Exception) {
                    Log.w("modelException", "Exception: $e")
                }
            }

            _uiState.update {
                it.copy(
                    userPairList = mutableUserPairsList.toList()
                )
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Exception: $e")
        }
    }
    fun dashboardInit() = CoroutineScope(Dispatchers.IO).launch {
        val mutableJobList: MutableList<JobData> = mutableListOf()

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
    fun updateCurrentMessages(userPair: UserPair, otherUserData: UserData)  = CoroutineScope(Dispatchers.IO).launch {
        val mutableMessageList: MutableList<MessageData> = mutableListOf()
        val otherUserId = if (userPair.userId1.equals(userData.userId)) {
            userPair.userId2!!
        } else {
            userPair.userId1!!
        }

        Log.e("user", "${otherUserData}")


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
        searchInit()

        val filteredJobList = _uiState.value.currentJobList.filter { jobData ->
            // Define your condition here, for example, checking if searchString appears in any of the strings
            val containsSearchString = listOf(
                jobData.title,
                jobData.pet,
                jobData.description,
                jobData.pay,
                jobData.location
            ).any { it?.contains(searchString, ignoreCase = true) == true }

            // Include the jobData in the filtered list if it meets the condition
            containsSearchString
        }

        _uiState.update {
            it.copy(
                currentJobList = filteredJobList
            )
        }

    }



}
