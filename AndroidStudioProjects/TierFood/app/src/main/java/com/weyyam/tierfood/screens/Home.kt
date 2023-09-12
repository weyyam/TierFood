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
import com.weyyam.tierfood.Search
import com.weyyam.tierfood.navbars.BottomBarAppView
import com.weyyam.tierfood.navbars.TopBarAppView


@Composable
fun HomeScreen(navController: NavHostController){


    Column(modifier = Modifier.fillMaxSize()) {
        TopBarAppView(navController = navController)
        Box(modifier = Modifier.weight(1f)
        ){
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "You are in Home Screen")
                Spacer(modifier = Modifier.weight(1f))
                BottomBarAppView(navController = navController)
            }

        }
    }



}