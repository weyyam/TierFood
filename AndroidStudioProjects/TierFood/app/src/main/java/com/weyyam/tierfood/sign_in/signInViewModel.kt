package com.weyyam.tierfood.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    /*
    fun onSignInResult(result: SignInResult){
        Log.i("REGISTER_C", "OnSignInResult (stateUpdates)")
        _state.update { it.copy(
            isSignInSuccessful = result.data !=null,
            signInError = result.errorMessage
        ) }
    }

     */

    fun onSignInResult(result: SignInResult){
        try {
            Log.d("REGISTER_C", "OnSignInResult fun is called")
            Log.d("VIEWMODEL", "_state before update: $_state.value")
            val newState = SignInState(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )

            if (result.errorMessage != null){
                Log.e("SIGNIN_ERROR", "Error during sign-in: ${result.errorMessage}")
            }

            if (_state.value != newState){
                _state.value = newState
                Log.d("REGISTER_C", "STATE CHANGE onsigninresult")
            }
            Log.d("VIEWMODEL", "_state after update: $_state.value")
        } catch (e: Exception) {
            Log.e("SIGNIN_ERROR", "Exception in onSignInResult", e)
        }
    }


    fun resetState(){
        Log.i("REGISTER_C", "resetState called")
        _state.update { SignInState() }
    }
}