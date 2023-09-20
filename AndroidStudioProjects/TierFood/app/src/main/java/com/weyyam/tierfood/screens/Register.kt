package com.weyyam.tierfood.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.IdentityProviders
import com.google.android.gms.auth.api.identity.CredentialSavingClient
import com.weyyam.tierfood.Home
import com.weyyam.tierfood.R
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

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background_SecondaryL)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Welcome to Tier Food!",
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        )

        Button(
            onClick = { Log.d("SignIn", "Button is pressed")
                onSignInClick()
            }) {
            Text(text = "Google SignIn")

        }
    }


    

}


@Composable
@Preview(showBackground = true)
fun previewRegister(){
    RegisterScreen(
        navController = rememberNavController(),
        onSignInClick = {},
        state = SignInState()
    )
}

