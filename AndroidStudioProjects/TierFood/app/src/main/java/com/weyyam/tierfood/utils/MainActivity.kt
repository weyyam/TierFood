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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.weyyam.tierfood.navigation.FoodProfile
import com.weyyam.tierfood.navigation.FoodsList
import com.weyyam.tierfood.navigation.Home
import com.weyyam.tierfood.navigation.NutrientProfile
import com.weyyam.tierfood.navigation.Profile
import com.weyyam.tierfood.navigation.Register
import com.weyyam.tierfood.navigation.Search
import com.weyyam.tierfood.screens.FoodProfileScreen
import com.weyyam.tierfood.screens.HomeScreen
import com.weyyam.tierfood.screens.NutrientProfileScreen
import com.weyyam.tierfood.screens.ProfileScreen
import com.weyyam.tierfood.screens.RegisterScreen
import com.weyyam.tierfood.screens.SearchScreen
import com.weyyam.tierfood.sign_in.GoogleAuthUiClient
import com.weyyam.tierfood.sign_in.SignInViewModel
import com.weyyam.tierfood.ui.favorite.UserFavoritesManager
import com.weyyam.tierfood.ui.theme.TierFoodTheme
import com.weyyam.tierfood.ui.widgets.FoodsListScreen
import com.weyyam.tierfood.viewmodels.FoodViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("LIFECYCLE", "MainActivity onCreate called")
         val localGoogleAuthUiClient = GoogleAuthUiClient(
                context = applicationContext,
                oneTapClient = Identity.getSignInClient(applicationContext),
                initializeFavorites = ::initializeUserFavorites
            )

        setContent {
            TierFoodTheme {
                MyNavigation(localGoogleAuthUiClient)

            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("LIFECYCLE", "MainActivity onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("LIFECYCLE", "MainActivity onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("LIFECYCLE", "MainActivity onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("LIFECYCLE", "MainActivity onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LIFECYCLE", "MainActivity onDestroy called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("LIFECYCLE", "MainActivity onRestart called")
    }


    private fun initializeUserFavorites(userId: String){
        val userFavoritesManager = UserFavoritesManager(userId)
        userFavoritesManager.initializeUserFavorites()
    }





    @Composable
    fun MyNavigation(googleAuthUiClient: GoogleAuthUiClient){

        val navController = rememberNavController()


        val currentUser = remember { mutableStateOf(googleAuthUiClient.getSignedInUser())}
        val userFavoritesManager = if (currentUser != null){
            currentUser.value?.let {
                UserFavoritesManager(it.userId).also {
                    it.initializeUserFavorites()
                }
            }
        } else {
            null
        }





        NavHost(navController = navController, startDestination = Register.route){



            composable(Register.route){
                Log.i("REGISTER_C", "Nav regster.route composable has started")
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()


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
                    if (state.isSignInSuccessful){
                        currentUser.value = googleAuthUiClient.getSignedInUser()
                    }
                }


                Log.i("SIGNIN_MAIN", "Register composable has recomposed")
                LaunchedEffect(key1 = state.isSignInSuccessful){
                    val isUserAlreadySignedIn = googleAuthUiClient.getSignedInUser() != null
                    Log.i("SIGNIN_MAIN", "user already signed in is:${isUserAlreadySignedIn}")
                    if(isUserAlreadySignedIn && !state.isSignInSuccessful){
                        Log.i("SIGNIN_MAIN", "User already signed in and starting auto sign in")
                        delay(100)
                        navController.navigate(Home.route){
                            popUpTo(Register.route){ inclusive = true }
                            launchSingleTop =true
                        }
                    } else {
                        Log.i("SIGNIN_MAIN", "isUserAlreadySignedIn is false and state.isSignInSuccessful is :${state.isSignInSuccessful}")
                        if(state.isSignInSuccessful){
                            navController.navigate(Home.route) {
                                popUpTo(Register.route) { inclusive = true }
                                launchSingleTop = true
                            }
                            viewModel.resetState()
                        }
                    }
                }

                RegisterScreen(
                    state = state,
                    navController = navController,
                    googleAuthUiClient = googleAuthUiClient,
                    onSignInClick = {
                        Log.d("SignIn_try", "Sign in button clicked")
                        lifecycleScope.launch{
                            Log.d("SignIn_try", "Inside corutine")
                            try {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                Log.d("SignIn_try", "Receved intent sender: $signInIntentSender")

                                if(signInIntentSender != null){
                                    launcher.launch(IntentSenderRequest.Builder(signInIntentSender).build())
                                }else{
                                    Log.e("SignIn_try", "signInIntentSender is null")
                                }
                            }catch (e: Exception){
                                Log.e("SignIn_try", "Error during signIn", e)
                            }
                        }


                    }
                )
                Log.i("REGISTER_C", "End of register composable")

            }
            composable(Home.route){
                Log.i("HOMESCREENN", "Home screen has been opened")
                Log.i("HOMESCREENN", "current user is $currentUser")
                currentUser.value?.let { it1 -> HomeScreen(navController, userId = it1.userId) }
                Log.i("SPlASH", "currentUser.userId is: $currentUser.userId")
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
                                Toast.LENGTH_SHORT
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
                    FoodsListScreen(viewModel = FoodViewModel(), category = category, navController = navController)
                }
            }


            composable("${FoodProfile.route}/{Id}"){ navBackStackEntry ->
                Log.d("FP", FoodProfile.route)
                val foodId = navBackStackEntry.arguments?.getString("Id") ?: return@composable
                Log.d("FP", "FoodProfile before navcontoller")
                Log.i("TESTING", "The foodId before launching the FoodProfileScreen is $foodId")
                if (userFavoritesManager != null) {
                    FoodProfileScreen(navController = navController, foodId = foodId, userFavoritesManager = userFavoritesManager,viewModel = FoodViewModel())
                }

            }

            composable("${NutrientProfile.route}/{nutrientName}"){ navBackStackEntry ->
                val nutrient = navBackStackEntry.arguments?.getString("nutrientName") ?: return@composable
                NutrientProfileScreen(navController = navController, nutrient = nutrient)
            }

        }
    }
}
