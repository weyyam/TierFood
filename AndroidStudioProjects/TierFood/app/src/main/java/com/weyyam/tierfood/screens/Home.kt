package com.weyyam.tierfood.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.Home
import com.weyyam.tierfood.R
import com.weyyam.tierfood.Search
import com.weyyam.tierfood.Tier
import com.weyyam.tierfood.navbars.BottomBarAppView
import com.weyyam.tierfood.navbars.TopBarAppView
import com.weyyam.tierfood.s_rank
import com.weyyam.tierfood.widgets.FoodOfDayCard
import com.weyyam.tierfood.widgets.TiersColumn


@Composable
fun HomeScreen(navController: NavHostController){
    val current_tier = s_rank

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarAppView(navController = navController)
        Box(modifier = Modifier.weight(1f)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background_light))
            ) {
                FoodOfDayCard(tier = current_tier)
                Text(
                    text = "These are your saved Foods!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.background_light)),
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
