package com.weyyam.tierfood.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun TierRow(tier: Tier, foods: List<FoodItem>){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(75.dp)
        .background(colorResource(id = R.color.background_SecondaryL)),
        verticalAlignment = Alignment.CenterVertically,


        ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .size(65.dp)
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
                        .size(65.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(15)))

            }
        }
    }
    Divider(color = colorResource(id = R.color.background_PrimaryD), thickness = 1.dp)

}

@Composable
fun TiersColumn(favoriteFoods: List<FoodItem>){

    val sRankFoods = favoriteFoods.filter { it.tier == "S" }
    val aRankFoods = favoriteFoods.filter { it.tier == "A" }
    val bRankFoods = favoriteFoods.filter { it.tier == "B" }
    val cRankFoods = favoriteFoods.filter { it.tier == "C" }
    val dRankFoods = favoriteFoods.filter { it.tier == "D" }
    val fRankFoods = favoriteFoods.filter { it.tier == "F" }

    Column() {
        TierRow(tier = s_rank, foods = sRankFoods)
        TierRow(tier = a_rank, foods = aRankFoods)
        TierRow(tier = b_rank, foods = bRankFoods)
        TierRow(tier = c_rank, foods = cRankFoods)
        TierRow(tier = d_rank, foods = dRankFoods)
        TierRow(tier = f_rank, foods = fRankFoods)
    }

}

@Composable
@Preview(showBackground = true)
fun Previewtierrow(){
    //TiersColumn()
}