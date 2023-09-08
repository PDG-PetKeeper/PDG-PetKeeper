package com.projet.petkeeper.sign_in

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This class represents the view model of the sign-in process
 */
class SignInViewModel: ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    /**
     * This function updates the state of the sign-in process.
     * @param result The result of the sign-in process.
     */
    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    /**
     * This function resets the state of the sign-in process.
     */
    fun resetState() {
        _state.update { SignInState() }
    }
}