package com.weyyam.tierfood.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.R
import com.weyyam.tierfood.navigation.NutrientProfile
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
        tint = Color.Unspecified
    )
}


@Composable
fun DisplayMapDataMacro(mapData: Map<String, Double>?, onClick: (String) -> Unit){
    Column {
        mapData
            ?.mapValues { it.value.toInt() }
            ?.entries
            ?.sortedByDescending {it.value}
            ?.forEach { (key, value) ->
            val displayText = if (key.lowercase() == "calories") {
                "${formatVitaminName(key)}: ${value}kcal"
            }else if (key.lowercase() == "gi"){
                "Glycemic Index: $value"
            } else {
                "${formatVitaminName(key)}: ${value}g"
            }
                Text(
                    text = displayText,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 10.dp)
                        .clickable { onClick(formatVitaminName(key)) }
                )
        }
    }
}

@Composable
fun DisplayMapDataMicro(mapData: Map<String, Double>?, onClick: (String) -> Unit){
    Column {
        mapData
            ?.mapValues { it.value.toInt() }
            ?.entries
            ?.sortedByDescending { it.value }
            ?.forEach { (key, value) ->
            Text(
                text = "${formatVitaminName(key)}: ${value}%",
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 10.dp)
                    .clickable { onClick(formatVitaminName(key)) })
        }
    }
}


fun formatVitaminName(name: String): String{
    return name.split('_')
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}







@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodProfileScreen(
    navController: NavController,
    foodId: String,
    userFavoritesManager: UserFavoritesManager,
    viewModel: FoodViewModel = viewModel()
){


    val foodItem = viewModel.getFoodById(foodId)
    val scrollState = rememberScrollState()


    Log.i("FPS", "FoodProfileScreen Composable has run with the food item ${foodItem}")



    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = foodItem?.imageURL)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(R.drawable.fruits_category)
            }).build()
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val imageSize = screenWidth * 0.375f

    Scaffold (
        topBar = { TopBarAppView(navController = rememberNavController(), modifier = Modifier)},
        bottomBar = { BottomBarAppView(navController = rememberNavController(), modifier = Modifier)},
        contentColor = colorResource(id = R.color.black)
    ){ innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            if (foodItem != null){

                FlowRow(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .background(colorResource(id = R.color.background_SecondaryL))
                    .padding(8.dp)
                ) {
                    Column (modifier = Modifier.fillMaxWidth(0.6f)){
                        Row(){
                            TierBoxFoodProfile(tier = foodItem.tier)
                            ClickableStar(foodId = foodId, userFavoritesManager = userFavoritesManager)
                        }
                        Text(
                            text = foodItem.name,
                            modifier = Modifier.weight(0.2f),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Nutritional Data is Based on 100g of Food",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .weight(0.2f)
                                .padding(vertical = 4.dp))
                    }

                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(0.4f)
                            .size(imageSize)
                            .clip(RoundedCornerShape(15))
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = colorResource(id = R.color.background_SecondaryL))
                        .weight(0.75f)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = foodItem.description,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                colorResource(id = R.color.background_PrimaryD),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Box (modifier = Modifier
                            .weight(0.5f)
                            .padding(8.dp)
                            .background(
                                colorResource(id = R.color.background_PrimaryD),
                                shape = RoundedCornerShape(10.dp)
                            )
                        ){
                            Column {
                                Text(text = "Macro Nutrients",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(10.dp)
                                )

                                DisplayMapDataMacro(
                                    foodItem.macros,
                                    onClick = { selectedNutrient ->
                                        navController.navigate("${NutrientProfile.route}/${selectedNutrient}")
                                    })

                            }

                        }

                        Box (
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(8.dp)
                                .background(
                                    colorResource(id = R.color.background_PrimaryD),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ){
                            Column {
                                Text(text = "Micro Nutrients",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                                DisplayMapDataMicro(
                                    foodItem.micros,
                                    onClick = { selectedNutrient ->
                                        navController.navigate("${NutrientProfile.route}/${selectedNutrient}")
                                    })
                            }

                        }
                    }
                }
            }

        }

    }
}


