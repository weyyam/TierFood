package com.weyyam.tierfood.navbars

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.weyyam.tierfood.Home
import com.weyyam.tierfood.Profile
import com.weyyam.tierfood.R
import com.weyyam.tierfood.Search

@Composable
fun TopBarAppView(navController: NavController){
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Image(
            painter = painterResource(id = R.drawable.round_keyboard_arrow_left_24),
            contentDescription = "Back arrow",
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable { navController.navigate(Home.route) }
                .size(36.dp),
            contentScale = ContentScale.Fit)

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
                .clickable { navController.navigate(Profile.route) }
                .size(36.dp),
            contentScale = ContentScale.Fit)
    }
}