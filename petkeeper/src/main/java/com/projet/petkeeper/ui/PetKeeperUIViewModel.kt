package com.projet.petkeeper.ui

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.projet.petkeeper.utils.Constants.TAG

class PetKeeperUIViewModel (val userData: UserData) : ViewModel() {

    private val firestoreDB = Firebase.firestore

    private val _uiState = MutableStateFlow(PetKeeperUIState())
    val uiState: StateFlow<PetKeeperUIState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {

        _uiState.value = PetKeeperUIState(
            //currentJobList = JobDataExample.jobDataExampleList
        )
    }

    fun searchInit(){
        var mutableJobList: MutableList<JobData>
        firestoreDB.collection("")
    }

    fun changeNavBarCurentIndex(index: Int){
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