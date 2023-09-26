package com.weyyam.tierfood.utils

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.weyyam.tierfood.navigation.FoodsList
import com.weyyam.tierfood.navigation.Home
import com.weyyam.tierfood.navigation.Profile
import com.weyyam.tierfood.navigation.Register
import com.weyyam.tierfood.navigation.Search
import com.weyyam.tierfood.screens.HomeScreen
import com.weyyam.tierfood.screens.ProfileScreen
import com.weyyam.tierfood.screens.RegisterScreen
import com.weyyam.tierfood.screens.SearchScreen
import com.weyyam.tierfood.sign_in.GoogleAuthUiClient
import com.weyyam.tierfood.sign_in.SignInViewModel
import com.weyyam.tierfood.ui.theme.TierFoodTheme
import com.weyyam.tierfood.ui.widgets.FoodViewModel
import com.weyyam.tierfood.ui.widgets.FoodsListScreen
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
                    navController = navController,
                    userData = googleAuthUiClient.getSignedInUser(),
                    onSignOut = {
                        lifecycleScope.launch {
                            googleAuthUiClient.signOut()
                            Toast.makeText(
                                applicationContext,
                                "Signed out",
                                Toast.LENGTH_LONG
                            ).show()
                            navController.navigate(Register.route){
                                popUpTo(Home.route){inclusive = true}
                            }

                        }
                    })
            }
            composable(Search.route){
                SearchScreen(navController)
            }

            composable(FoodsList.route){ navBackStackEntry ->
                val category = navBackStackEntry.arguments?.getString("category")
                Log.d("FL","Before if statement in navcontroller")
                if (category != null){
                    Log.d("FL","FoodsListScreen if category statement passes")
                    FoodsListScreen(viewModel = FoodViewModel(), category = category)
                }
            }
        }
    }
}