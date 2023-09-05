package com.projet.petkeeper.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GeneralUIViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GeneralUIState())
    val uiState: StateFlow<GeneralUIState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {

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

}