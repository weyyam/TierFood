package com.weyyam.tierfood.ui.navbars

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.navigation.Home
import com.weyyam.tierfood.navigation.Profile
import com.weyyam.tierfood.R
import com.weyyam.tierfood.navigation.Search

@Composable
fun BottomBarAppView(navController : NavController){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(colorResource(id = R.color.background_PrimaryD)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
        ){

        Image(
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable { navController.navigate(Search.route) }
                .size(36.dp),
            painter = painterResource(id = R.drawable.baseline_search_24) ,
            contentDescription = "Person for Profile",
            contentScale = ContentScale.Fit)

        Image(
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable { navController.navigate(Home.route) }
                .size(36.dp),
            painter = painterResource(id = R.drawable.baseline_home_24) ,
            contentDescription = "Person for Profile",
            contentScale = ContentScale.Fit)

        Image(
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable { navController.navigate(Profile.route) }
                .size(36.dp),
            painter = painterResource(id = R.drawable.baseline_person_24) ,
            contentDescription = "Person for Profile",
            contentScale = ContentScale.Fit)

    }

}


@Composable
@Preview(showBackground = true)
fun previewBottomAppBar(){
    BottomBarAppView(navController = rememberNavController())
}