package com.weyyam.tierfood

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.weyyam.tierfood.ui.theme.TierFoodTheme
import com.weyyam.tierfood.screens.HomeScreen
import com.weyyam.tierfood.screens.ProfileScreen
import com.weyyam.tierfood.screens.RegisterScreen
import com.weyyam.tierfood.screens.SearchScreen
import com.weyyam.tierfood.sign_in.GoogleAuthUiClient
import com.weyyam.tierfood.sign_in.SignInResult
import com.weyyam.tierfood.sign_in.SignInState
import com.weyyam.tierfood.sign_in.SignInViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TierFoodTheme {
                MyNavigation()

            }
        }
    }
    @Composable
    fun MyNavigation(){

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Register.route){

            composable(Register.route){
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit){
                    if(googleAuthUiClient.getSignedInUser() != null){
                        navController.navigate(Home.route){
                            popUpTo(Register.route){inclusive = true}
                        }
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == Activity.RESULT_OK){
                            lifecycleScope.launch{
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)

                            }
                        }
                    }
                )



                LaunchedEffect(key1 = state.isSignInSuccessful){
                    if(state.isSignInSuccessful){
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(Home.route){
                            popUpTo(Register.route){inclusive = true}
                        }
                        viewModel.resetState()
                    }
                }

                RegisterScreen(
                    state = state,
                    navController = navController,
                    onSignInClick = {
                        Log.d("SignIn", "Sign in button clicked")
                        lifecycleScope.launch{
                            Log.d("SignIn", "Inside corutine")
                            try {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                Log.d("SignIn", "Receved intent sender: $signInIntentSender")

                                if(signInIntentSender != null){
                                    launcher.launch(IntentSenderRequest.Builder(signInIntentSender).build())
                                }else{
                                    Log.e("SignIn", "signInIntentSender is null")
                                }
                            }catch (e: Exception){
                                Log.e("SignIn", "Error during signIn", e)
                            }
                        }


                    }
                )

            }
            composable(Home.route){
                HomeScreen(navController)
            }
            composable(Profile.route){
                ProfileScreen(
                    navController,
                    onSignOut = {
                        lifecycleScope.launch {
                            googleAuthUiClient.signOut()
                            Toast.makeText(
                                applicationContext,
                                "Signed out",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.popBackStack()

                        }
                })
            }
            composable(Search.route){
                SearchScreen(navController)
            }
        }
    }
}
