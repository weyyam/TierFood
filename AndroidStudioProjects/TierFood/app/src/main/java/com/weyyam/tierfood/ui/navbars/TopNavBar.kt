package com.weyyam.tierfood.ui.navbars

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.weyyam.tierfood.navigation.Home
import com.weyyam.tierfood.navigation.Profile
import com.weyyam.tierfood.R

@Composable
fun TopBarAppView(navController: NavController){
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(colorResource(id = R.color.background_PrimaryD)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        Image(
            painter = painterResource(id = R.drawable.twotone_logo_dev_24),
            contentDescription = "Temp Logo for the top nav bar",
            alignment = Alignment.Center,
            modifier = Modifier.size(36.dp),
            contentScale = ContentScale.Fit)

        Image(
            painter = painterResource(id = R.drawable.baseline_person_24),
            contentDescription = "Temp holding of profile image while we wait for profile image to be added",
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable {
                    if(navController.currentDestination != null){
                        navController.navigate(Profile.route)
                    } else {
                        Log.d("NAV", "NavController.currentDestination fail because of null")
                    }


                }
                .size(36.dp),
            contentScale = ContentScale.Fit)
    }
}