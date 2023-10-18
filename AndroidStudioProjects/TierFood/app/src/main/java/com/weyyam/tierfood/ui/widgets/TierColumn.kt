package com.weyyam.tierfood.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.R
import com.weyyam.tierfood.data.FoodItem
import com.weyyam.tierfood.model.Tier
import com.weyyam.tierfood.model.a_rank
import com.weyyam.tierfood.model.b_rank
import com.weyyam.tierfood.model.c_rank
import com.weyyam.tierfood.model.d_rank
import com.weyyam.tierfood.model.f_rank
import com.weyyam.tierfood.model.s_rank
import com.weyyam.tierfood.navigation.FoodProfile

@Composable
fun TierRow(tier: Tier, foods: List<FoodItem>, onClick: (FoodItem) -> Unit){

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val minDimension = minOf(screenWidthDp, screenHeightDp)
    val density = LocalDensity.current.density
    val imageSize = (minDimension.value / 6.5).dp

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(imageSize + 10.dp)
        .background(colorResource(id = R.color.background_SecondaryL)),
        verticalAlignment = Alignment.CenterVertically,


        ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .size(imageSize + 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = tier.color)),
            contentAlignment = Alignment.Center){
            Text(
                text = tier.letter,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }

        LazyRow{
            items(foods){ food ->
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = food.imageURL)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.fruit_category)
                        }).build())

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(imageSize)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(15))
                        .clickable { onClick(food) })

            }
        }
    }
    Divider(color = colorResource(id = R.color.background_PrimaryD), thickness = 1.dp)

}

@Composable
fun TiersColumn(favoriteFoods: List<FoodItem>, navController: NavController){

    val sRankFoods = favoriteFoods.filter { it.tier == "S" }
    val aRankFoods = favoriteFoods.filter { it.tier == "A" }
    val bRankFoods = favoriteFoods.filter { it.tier == "B" }
    val cRankFoods = favoriteFoods.filter { it.tier == "C" }
    val dRankFoods = favoriteFoods.filter { it.tier == "D" }
    val fRankFoods = favoriteFoods.filter { it.tier == "F" }

    Column() {
        TierRow(tier = s_rank, foods = sRankFoods, onClick = {selectedFood ->
            navController.navigate("${FoodProfile.route}/${selectedFood.id}")
        })
        TierRow(tier = a_rank, foods = aRankFoods, onClick = {selectedFood ->
            navController.navigate("${FoodProfile.route}/${selectedFood.id}")
        })
        TierRow(tier = b_rank, foods = bRankFoods, onClick = {selectedFood ->
            navController.navigate("${FoodProfile.route}/${selectedFood.id}")
        })
        TierRow(tier = c_rank, foods = cRankFoods, onClick = {selectedFood ->
            navController.navigate("${FoodProfile.route}/${selectedFood.id}")
        })
        TierRow(tier = d_rank, foods = dRankFoods, onClick = {selectedFood ->
            navController.navigate("${FoodProfile.route}/${selectedFood.id}")
        })
        TierRow(tier = f_rank, foods = fRankFoods, onClick = {selectedFood ->
            navController.navigate("${FoodProfile.route}/${selectedFood.id}")
        })
    }

}

@Composable
@Preview(showBackground = true)
fun Previewtierrow(){
    //TiersColumn()
}