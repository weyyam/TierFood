package com.weyyam.tierfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.ui.theme.TierFoodTheme
import com.weyyam.tierfood.Home
import com.weyyam.tierfood.Register
import com.weyyam.tierfood.Profile
import com.weyyam.tierfood.Search


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    NavHost(navController = navController, startDestination = Home.route){

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