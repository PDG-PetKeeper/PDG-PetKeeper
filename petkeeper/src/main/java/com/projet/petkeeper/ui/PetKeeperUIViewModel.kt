package com.projet.petkeeper.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetKeeperUIViewModel (val userData: UserData, private val coroutineScope: CoroutineScope) : ViewModel() {

    private val firestoreDB = Firebase.firestore

    private val _uiState = MutableStateFlow(PetKeeperUIState())
    val uiState: StateFlow<PetKeeperUIState> = _uiState


    init {
        initializeUIState()
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

    fun showNavBar(){
        _uiState.update {
            it.copy(
                mustShowNavBar = true
            )
        }
    }

    fun hideNavBar(){
        _uiState.update {
            it.copy(
                mustShowNavBar = false
            )
        }
    }

    fun updateSelectedJob(jobData: JobData?){
        _uiState.update {
            it.copy(
                currentSelectedJob = jobData
            )
        }
    }

    fun searchJobs(searchString: String){

    }

    fun searchChats(searchString: String){

    }





}