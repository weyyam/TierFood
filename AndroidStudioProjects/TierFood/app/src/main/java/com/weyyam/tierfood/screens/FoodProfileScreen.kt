package com.weyyam.tierfood.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
        tint = Color.Unspecified)
}



@Composable
fun FoodProfileScreen(
    navController: NavController,
    foodId: String,
    userFavoritesManager: UserFavoritesManager,
    viewModel: FoodViewModel = viewModel()) {


    val foodItem = viewModel.getFoodById(foodId)
    val scrollState = rememberScrollState()
    var imageBottomY by remember { mutableStateOf(0.dp)}
    var bottomBarTopY by remember { mutableStateOf(0.dp)}

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




    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background_SecondaryL))
    ) {
        val (topBar, image, title, star, tierBox, scrollable, bottomBar, info) = createRefs()

        val guidelineTop = createGuidelineFromTop(imageBottomY) // Assuming top bar height
        val guidelineBottom = createGuidelineFromBottom(bottomBarTopY) // Assuming bottom bar height
        val density = LocalDensity.current


        TopBarAppView(
            navController = navController,
            modifier = Modifier
                .constrainAs(topBar) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        /**
         * FoodImage,name,star,tier top Row
         */
        val topBarHeight = 56.dp


        Image(
            painter = painter,
            contentDescription = "Image of Food in question",
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(15))
                .onGloballyPositioned { layoutCoordinates ->
                    val pixelValue =
                        layoutCoordinates.positionInRoot().y.toInt() + layoutCoordinates.size.height.toInt()
                    //imageBottomY = with(density) { pixelValue.toDp()}
                    imageBottomY = 210.dp
                    Log.d("LAYOUT", "imagebottomY: $imageBottomY")
                }
                .constrainAs(image) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                }
        )

        if (foodItem != null) {
            Text(
                text = foodItem.name,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .constrainAs(title) {
                        top.linkTo(image.top)
                        start.linkTo(image.end)
                        end.linkTo(star.start)
                    },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Text(
            text = "Nutritional Data is Based on 100g of Food",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(info) {
                    start.linkTo(image.end)
                    bottom.linkTo(image.bottom)
                })
        Box(
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(star) {
                    top.linkTo(topBar.bottom)
                    end.linkTo(tierBox.start)
                }) {
            ClickableStar(foodId = foodId, userFavoritesManager = userFavoritesManager)

        }

        Box(modifier = Modifier.constrainAs(tierBox) {
            top.linkTo(topBar.bottom)
            end.linkTo(parent.end)
        }) {
            if (foodItem != null) {
                TierBoxFoodProfile(tier = foodItem.tier)
            }
        }


        /**
         * description aswell as Micro and Macro Info for food
         */


        Column(modifier =
        Modifier
            .padding(top = 125.dp, bottom = 120.dp)
            .verticalScroll(scrollState)
            .constrainAs(scrollable) {
                top.linkTo(guidelineTop)
                bottom.linkTo(guidelineBottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }) {



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        colorResource(id = R.color.background_PrimaryD),
                        shape = RoundedCornerShape(8)
                    )


            ) {
                if (foodItem != null) {
                    Text(
                        text = foodItem.description,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 20.dp)
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
                        .background(
                            colorResource(id = R.color.background_PrimaryD),
                            shape = RoundedCornerShape(8)
                        )
                ) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = "Macro Nutrients",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        if (foodItem != null) {
                            DisplayMapDataMacro(
                                foodItem.macros,
                                onClick = { selectedNutrient ->
                                    navController.navigate("${NutrientProfile.route}/${selectedNutrient}")
                                })
                        }
                    }


                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(
                            colorResource(id = R.color.background_PrimaryD),
                            shape = RoundedCornerShape(8)
                        )
                ) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = "Micros Nutrients",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        if (foodItem != null) {
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





        BottomBarAppView(
            navController = navController,
            modifier = Modifier

                .constrainAs(bottomBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .onGloballyPositioned { layoutCoordinates ->
                    val pixelValue = layoutCoordinates.positionInRoot().y.toInt()
                    //bottomBarTopY = with(density) { pixelValue.toDp()}
                    bottomBarTopY = 60.dp
                    Log.d("LAYOUT", "bottomBarTopY: $bottomBarTopY")
                }
        )
    }

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
                        .padding(4.dp)
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
                    .padding(4.dp)
                    .clickable { onClick(formatVitaminName(key)) })
        }
    }
}


fun formatVitaminName(name: String): String{
    return name.split('_')
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}



