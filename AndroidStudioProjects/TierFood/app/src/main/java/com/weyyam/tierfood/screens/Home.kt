package com.weyyam.tierfood.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.R
import com.weyyam.tierfood.ui.navbars.BottomBarAppView
import com.weyyam.tierfood.ui.navbars.TopBarAppView
import com.weyyam.tierfood.model.s_rank
import com.weyyam.tierfood.ui.widgets.FoodOfDayCard
import com.weyyam.tierfood.ui.widgets.TiersColumn


@Composable
fun HomeScreen(navController: NavHostController){

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarAppView(navController = navController)
        Box(modifier = Modifier.weight(1f)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background_PrimaryD))
            ) {
                FoodOfDayCard()
                Text(
                    text = "These are your saved Foods!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.background_PrimaryD)),
                    textAlign = TextAlign.Center
                )
                TiersColumn()
                Spacer(modifier = Modifier.weight(1f))
                BottomBarAppView(navController = navController)
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun previewHomeScreen(){
    HomeScreen(navController = rememberNavController())
}
