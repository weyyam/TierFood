package com.weyyam.tierfood.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.weyyam.tierfood.Home


@Composable
fun RegisterScreen(navController: NavHostController){

    Button(onClick = {navController.navigate(Home.route)}) {
        Text(text = "Sign in go to home ")
    }
}




