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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date

/**
 * ViewModel class responsible for managing the state and logic of the PetKeeper user interface.
 * This ViewModel handles various data retrieval and manipulation operations related to the app's UI.
 */
open class PetKeeperUIViewModel : ViewModel() {

    /**
     * Singleton object representing the ViewModel instance. Use this object for accessing ViewModel
     * methods and properties.
     */
    object ViewModel: PetKeeperUIViewModel()

    // Firebase Firestore database instance
    private val firestoreDB = Firebase.firestore

    // Mutable state flow for UI state management
    private val _uiState = MutableStateFlow(PetKeeperUIState())
    val uiState: StateFlow<PetKeeperUIState> = _uiState

    // User data associated with the current session
    lateinit var userData: UserData


    /**
     * Initializes the UI state and triggers the dashboard initialization.
     */
    init {
        initializeUIState()
    }
    /**
     * Initializes the UI state to its default values and triggers the dashboard initialization.
     */
    private fun initializeUIState(){
        _uiState.value = PetKeeperUIState()
        dashboardInit()
    }
    /**
     * Fetches user data from Firebase Firestore based on the given user ID and provides the
     * retrieved data to the specified fetcher function.
     *
     * @param userId The ID of the user to fetch data for.
     * @param fetcher The function to handle the fetched user data.
     */
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

    /**
     * Initializes the UI state for searching functionality, including retrieving a list of
     * available jobs based on user criteria.
     */
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

    /**
     * Initializes the UI state for chat functionality, including retrieving a list of user pairs
     * for chat conversations.
     */
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
                        Log.d("modelPairAdd", "Added user pair : $userPair")
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
    /**
     * Initializes the UI state for the dashboard, including retrieving a list of user's posted jobs.
     */
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

    /**
     * Initializes the UI state for the user profile view.
     */
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

    /**
     * Sends a chat message to the current chat conversation.
     *
     * @param messageString The message content to send.
     */
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

    /**
     * Retrieves and updates the list of chat messages in the current chat conversation.
     *
     * @param userPair The user pair associated with the chat.
     * @param otherUserData The user data of the chat conversation partner.
     */
    fun updateCurrentMessages(userPair: UserPair, otherUserData: UserData)  = CoroutineScope(Dispatchers.IO).launch {
        val mutableMessageList: MutableList<MessageData> = mutableListOf()

        try {
            val querySnapshot = firestoreDB.collection("userPairs")
                .document(userPair.id!!)
                .collection("messages")
                .orderBy("timestamp")
                .get()
                .await()

            for (document in querySnapshot.documents) {
                try {
                    val messageData = document.toObject<MessageData>()
                    if (messageData != null){
                        mutableMessageList.add(messageData)
                    }

                } catch (e: Exception) {
                    Log.w("JobAdd", "Exception: $e")
                }

            }
            withContext(Dispatchers.Main){
                _uiState.update {
                    it.copy(
                        currentMessageList = mutableMessageList.toList().reversed(),
                        currentChatter = otherUserData
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Exception: $e")
        }
    }

    /**
     * Searches for jobs based on a search query [searchString] and updates the UI state with
     * the filtered job list.
     *
     * @param searchString The search query to filter job listings.
     */
    fun searchJobs(searchString: String) = CoroutineScope(Dispatchers.IO).launch {

        runBlocking {
            searchInit()
        }

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

    /**
     * Changes the current index of the navigation bar item in the UI state.
     *
     * @param index The new index to set for the current navigation bar item.
     */

    fun changeNavBarCurrentIndex(index: Int){
        _uiState.update {
            it.copy(
                currentNavBarItemIndex = index
            )
        }
    }

    /**
     * Shows the navigation bar in the UI by updating the UI state.
     */
    fun showNavBar() {
        _uiState.update {
            it.copy(
                mustShowNavBar = true
            )
        }
    }

    /**
     * Hides the navigation bar in the UI by updating the UI state.
     */
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

    fun resetToChatRoot(){
        _uiState.update {
            it.copy(
                currentUserPair = null,
                currentChatter = null,
                currentMessageList = emptyList(),
            )
        }
    }
}
