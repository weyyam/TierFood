package com.weyyam.tierfood.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.IdentityProviders
import com.google.android.gms.auth.api.identity.CredentialSavingClient
import com.weyyam.tierfood.Home
import com.weyyam.tierfood.sign_in.SignInState


@Composable
fun RegisterScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    navController: NavController
){
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError){
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    
    Button(
        onClick = { Log.d("SignIn", "Button is pressed")
            onSignInClick()
    }) {
        Text(text = "Sign in")

    }
}




