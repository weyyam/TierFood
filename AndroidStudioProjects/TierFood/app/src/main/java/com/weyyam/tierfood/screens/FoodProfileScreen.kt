package com.weyyam.tierfood.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.R
import com.weyyam.tierfood.navigation.Profile
import com.weyyam.tierfood.ui.favorite.UserFavoritesManager
import com.weyyam.tierfood.ui.navbars.BottomBarAppView
import com.weyyam.tierfood.ui.navbars.TopBarAppView
import com.weyyam.tierfood.ui.widgets.TierBoxFoodProfile
import com.weyyam.tierfood.viewmodels.FoodViewModel

@Composable
fun ClickableStar(
    foodId: String,
    userFavoritesManager: UserFavoritesManager
){
    var isFavorited by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = foodId){
        userFavoritesManager.isFavorited(foodId){ result ->
            isFavorited = result
        }
    }

    Icon(
        painter = painterResource(
            id = if (isFavorited) R.drawable.ic_star_filled else R.drawable.ic_star_outline),
        contentDescription = "Star Icon",
        modifier = Modifier.clickable {

            isFavorited = !isFavorited
            if (isFavorited){
                userFavoritesManager.addFavorites(foodId, onSuccess = {}, onFailure = {})
            } else {
                userFavoritesManager.removeFavorites(foodId, onSuccess = {}, onFailure = {})
            }
        },
        tint = Color.Unspecified)
}



@Composable
fun FoodProfileScreen(
    navController: NavController,
    foodId: String,
    userFavoritesManager: UserFavoritesManager,
    viewModel: FoodViewModel = viewModel()) {


    val foodItem = viewModel.getFoodById(foodId)
    Log.i("FPS", "FoodProfileScreen Composable has run with the food item ${foodItem}")

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = foodItem?.imageURL)
            .apply(block = fun ImageRequest.Builder.(){
                crossfade(true)
                placeholder(R.drawable.fruit_category)
            }).build()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.background_SecondaryL)
            )
    ) {
        TopBarAppView(navController = navController)
        /**
         * FoodImage,name,star,tier top Row
         */


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painter,
                contentDescription = "Image of Food in question",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(15)),
                contentScale = ContentScale.Crop
            )

            if (foodItem != null) {
                Text(
                    text = foodItem.name,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier.padding(8.dp)){
                ClickableStar(foodId = foodId, userFavoritesManager = userFavoritesManager)

            }

            Column(){
                if (foodItem != null) {
                    TierBoxFoodProfile(tier = foodItem.tier)
                }
            }


        }
        /**
         * description aswell as Micro and Macro Info for food
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.black),
                    shape = RoundedCornerShape(15)
                )

        ){
            if (foodItem != null) {
                Text(
                    text = foodItem.description,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = 8.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(15)
                    )) {
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = "Macros:",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                    if (foodItem != null) {
                        DisplayMapDataMacro(foodItem.macros)
                    }
                }


            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(15)
                    )) {
                Column (modifier = Modifier.padding(4.dp)){
                    Text(
                        text = "Micros:",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                    if (foodItem != null) {
                        DisplayMapDataMicro(foodItem.micros)
                    }
                }

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        BottomBarAppView(navController = navController)
    }

}


@Composable
fun DisplayMapDataMacro(mapData: Map<String, Double>?){
    Column {
        mapData?.forEach { (key, value) ->
            Text(
                text = "$key: ${value}g",
                modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun DisplayMapDataMicro(mapData: Map<String, Double>?){
    Column {
        mapData?.forEach { (key, value) ->
            Text(
                text = "$key: ${value}%",
                modifier = Modifier.padding(4.dp))
        }
    }
}


