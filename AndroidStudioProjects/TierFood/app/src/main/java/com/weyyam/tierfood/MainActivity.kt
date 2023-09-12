package com.weyyam.tierfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.FirebaseApp
import com.weyyam.tierfood.ui.theme.TierFoodTheme
import com.weyyam.tierfood.screens.HomeScreen
import com.weyyam.tierfood.screens.ProfileScreen
import com.weyyam.tierfood.screens.RegisterScreen
import com.weyyam.tierfood.screens.SearchScreen


class MainActivity : ComponentActivity() {


    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest


    override fun onCreate(savedInstanceState: Bundle?) {

        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.Builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.Builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.your_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .setAutoSelectEnabled(true).build()
        setContent {
            TierFoodTheme {
                MyNavigation()

            }
        }
    }
}


@Composable
fun MyNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Register.route){

        composable(Register.route){
            RegisterScreen(navController)
        }
        composable(Home.route){
            HomeScreen(navController)
        }
        composable(Profile.route){
            ProfileScreen(navController)
        }
        composable(Search.route){
            SearchScreen(navController)
        }
    }
}