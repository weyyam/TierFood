package com.weyyam.tierfood.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.R
import com.weyyam.tierfood.Tier
import com.weyyam.tierfood.s_rank
import com.weyyam.tierfood.screens.HomeScreen


@Composable
fun FoodOfDayCard(tier: Tier){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(colorResource(id = R.color.background_dark))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.white)),
                    contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = "This will be the image of the food of the day")
                }

                Text(text = "Name_of_Food")
                // potentially make this its own class at some point
                //This is the S Box
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(id = tier.color)),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = tier.letter,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
            Text(text = "This is a quick description on the food in quesiton, ")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun previewFoodOfDayCard(){
    FoodOfDayCard(tier = s_rank)
}