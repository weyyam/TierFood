package com.weyyam.tierfood.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.navbars.BottomBarAppView


@Composable
fun SearchScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween)
        {
            Text(text = "You are in the search page")
            Spacer(modifier = Modifier.weight(1f))
            BottomBarAppView(navController = navController)
            //To make it such that the Bar stays on the bottom add lazy list and move BottomBarAppView out of the column

        }
    }




}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview(){
    SearchScreen(navController = rememberNavController())
}