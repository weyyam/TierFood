package com.weyyam.tierfood.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.weyyam.tierfood.Home
import com.weyyam.tierfood.navbars.BottomBarAppView
import com.weyyam.tierfood.navbars.TopBarAppView

@Composable
fun ProfileScreen(
    navController: NavHostController,
    onSignOut: () -> Unit){
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarAppView(navController = navController)
        Box(
            modifier = Modifier.weight(1f)
        ){
            Column(modifier =Modifier.fillMaxSize()) {
                Text(text = "You are in the Profile")
                Button(onClick = { onSignOut }) {
                    Text(text = "SignOut")
                }
                Spacer(modifier = Modifier.weight(1f))
                BottomBarAppView(navController = navController)
                //To make it such that the Bar stays on the bottom add lazy list and move BottomBarAppView out of the column
            }

        }
    }

    
}