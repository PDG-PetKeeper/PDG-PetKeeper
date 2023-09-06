package com.projet.petkeeper.ui

import androidx.lifecycle.ViewModel
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.JobDataExample
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PetKeeperUIViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PetKeeperUIState())
    val uiState: StateFlow<PetKeeperUIState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value = PetKeeperUIState(
            currentJobList = JobDataExample.jobDataExampleList
        )
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

    fun addJob(jobData: JobData){

    }

}