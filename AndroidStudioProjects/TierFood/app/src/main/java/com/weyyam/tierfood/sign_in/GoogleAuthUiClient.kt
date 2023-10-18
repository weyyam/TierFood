package com.weyyam.tierfood.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient (
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val initializeFavorites: (String) -> Unit
    ){

    private val LOG_TAG = "GoogleAuthUiClient"
    val WEB_CLIENT_ID: String = "768752322941-hmfr0au0rtnvmq8ujkc0el2imjejtjuq.apps.googleusercontent.com"

    private val auth = Firebase.auth

    suspend fun checkUserSignInStatus(): Boolean{
        Log.i("SPLASH", "Check for user signin status")
        Log.i("SPLASH", "The value of context is $context")
        val account = GoogleSignIn.getLastSignedInAccount(context)
        Log.i("SPLASH", "signin status is $account")
        return account != null
    }

    suspend fun signIn(): IntentSender? {
        Log.d(LOG_TAG, "sign in intent sender suspend function")
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e:Exception){
            e.printStackTrace()
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            if(user != null){
                initializeFavorites(user.uid)
            }

            SignInResult(
                data = user?.run{
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut(){
        try{
            auth.signOut()
        } catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest{
        Toast.makeText(context, "Sign in has started", Toast.LENGTH_SHORT).show()

        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}