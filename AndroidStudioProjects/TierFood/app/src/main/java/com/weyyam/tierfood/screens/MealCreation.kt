package com.weyyam.tierfood.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.weyyam.tierfood.R
import com.weyyam.tierfood.data.FoodItem
import com.weyyam.tierfood.ui.navbars.BottomBarAppView
import com.weyyam.tierfood.ui.navbars.TopBarAppView
import com.weyyam.tierfood.ui.widgets.NetworkImage
import com.weyyam.tierfood.ui.widgets.TierBoxFoodList
import com.weyyam.tierfood.ui.widgets.TierBoxMeal
import com.weyyam.tierfood.utils.calculateMacroNutrients
import com.weyyam.tierfood.utils.calculateMicroNutrients
import com.weyyam.tierfood.utils.noRippleClickable
import com.weyyam.tierfood.viewmodels.CameraViewModel
import com.weyyam.tierfood.viewmodels.FoodViewModel
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MealCreationScreen(
    navController: NavController,
    cameraViewModel: CameraViewModel
){

    val showDialog = remember { mutableStateOf(false) }
    val selectedFoods = remember { mutableStateListOf<FoodItem>() }
    val quantities = remember { mutableStateMapOf<String, Double>()}
    //un sure if this is correct with the current format i have going
    val imageUri by cameraViewModel.capturedImageUri.observeAsState()

    val focusManager = LocalFocusManager.current
    val defaultImage = R.drawable.beans_category

    val imagePainter = if (imageUri != null){
        rememberImagePainter(imageUri)
    } else {
        painterResource(id = defaultImage)
    }


    DisposableEffect(Unit){
        onDispose {
            cameraViewModel.resetImage()
        }
    }



    Scaffold(
        topBar = { TopBarAppView(navController = navController, modifier = Modifier)},
        bottomBar = { BottomBarAppView(navController = navController, modifier = Modifier)},
        containerColor = colorResource(id = R.color.background_SecondaryL),
        contentColor = colorResource(id = R.color.black)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorResource(id = R.color.background_SecondaryL))
                .noRippleClickable { focusManager.clearFocus() }
        ) {
            /**
             * Top segment with image name and tier
             */
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .paint(
                        painter = imagePainter,
                        contentScale = ContentScale.FillBounds
                    )
                    .clickable {
                        cameraViewModel.toggleCamera(true)
                    }
            ){
                var query by remember { mutableStateOf("")}

                TextField(
                    value = query,
                    onValueChange = {query = it},
                    trailingIcon = {
                        Icon(Icons.Default.Create,
                            contentDescription = "Create Icon")
                    },
                    placeholder = { Text(text = "Name your food")},
                    singleLine = true,
                    shape = RoundedCornerShape(15),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) ,
                    modifier = Modifier
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(15))
                        .padding(4.dp)
                        .align(Alignment.BottomStart)
                        .focusTarget(),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Black.copy(0.7f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ))

                TierBoxMeal("S", modifier = Modifier.align(Alignment.TopEnd))
            }

            /**
             * second half that is scrollable showing ingredents to add and nutritional info
             */

            LazyColumn(
                modifier = Modifier.fillMaxSize()

            ){
                item {
                    Box (
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .background(
                                colorResource(id = R.color.white),
                                RoundedCornerShape(8)
                            )
                            .padding(8.dp)
                    ){
                        Column (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ){


                            selectedFoods.forEach { ingredient ->
                                if (!quantities.containsKey(ingredient.id)){
                                    quantities[ingredient.id] = 1.0
                                }
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    NetworkImageMealIngredient(url = ingredient.imageURL)
                                    Text(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        text = ingredient.name,
                                        modifier = Modifier.padding(8.dp))
                                    val currentQuantity = quantities[ingredient.id] ?: 1.0
                                    IngredientTweaking(
                                        quantity = currentQuantity,
                                        onIncrease = {
                                            if (currentQuantity < 4.0) {
                                                quantities[ingredient.id] = currentQuantity + 0.25
                                            }
                                        },
                                        onDecrease = {
                                            if (currentQuantity > 0.25) {
                                                quantities[ingredient.id] = currentQuantity - 0.25
                                            } else {
                                                // Remove the item if quantity falls below 0.25
                                                quantities.remove(ingredient.id)
                                                selectedFoods.remove(ingredient)
                                            }
                                        })

                                }
                            }

                            IconButton(
                                onClick = { showDialog.value = true },
                                ) {
                                Icon(
                                    Icons.Rounded.Add,
                                    contentDescription = "Add New Ingredient",
                                    tint = colorResource(id = R.color.black))
                            }

                            if (showDialog.value){
                                var query by remember { mutableStateOf("") }
                                Dialog(onDismissRequest = { showDialog.value = false }) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        SearchBarMealCreation(query = query, onQueryChanged = {query = it})
                                        if (query.isNotEmpty()){
                                            ListOfSearchedFoodsMealCreation(
                                                query = query,
                                                onClick = { foodItem ->
                                                    selectedFoods.add(foodItem)
                                                    showDialog.value = false
                                                })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Box (
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .background(
                                colorResource(id = R.color.white),
                                RoundedCornerShape(8)
                            )
                            .padding(8.dp)
                    ){
                        val totalMacros = mutableMapOf<String, Double>()
                        selectedFoods.forEach {foodItem ->
                            val quantity = quantities[foodItem.id] ?: 1.0
                            val foodMacros = calculateMacroNutrients(foodItem, quantity) ?: emptyMap()

                            foodMacros.forEach{ (nutrient, value) ->
                                totalMacros[nutrient] = totalMacros.getOrDefault(nutrient, 0.0) + value
                            }

                        }
                        Column{
                            Text(text = "Macro Nutritional Values",
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp))
                            totalMacros
                                .mapValues { it.value.toInt() }
                                .entries
                                .sortedByDescending { it.value }
                                .forEach { entry ->

                                val displayText = if (entry.key.lowercase() == "calories") {
                                    "${formatVitaminName(entry.key)}: ${entry.value}kcal"
                                }else if (entry.key.lowercase() == "gi"){
                                    "Glycemic Index: ${entry.value}"
                                } else {
                                    "${formatVitaminName(entry.key)}: ${entry.value}g"
                                }
                                Text(
                                    text = displayText,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 10.dp)
                                )
                                //Text("${entry.key}: ${entry.value}")
                            }

                        }

                    }
                }

                item {
                    Box (
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .background(
                                colorResource(id = R.color.white),
                                RoundedCornerShape(8)
                            )
                            .padding(8.dp)
                    ){
                        val totalMicros = mutableMapOf<String, Double>()

                        selectedFoods.forEach {foodItem ->
                            val quantity = quantities[foodItem.id] ?: 1.0
                            val foodMicros = calculateMicroNutrients(foodItem, quantity) ?: emptyMap()

                            foodMicros.forEach{ (nutrient, value) ->
                            totalMicros[nutrient] = totalMicros.getOrDefault(nutrient, 0.0) + value
                            }
                        }

                        Column{
                            Text(
                                text = "Micro Nutritional Values",
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp))
                            totalMicros
                                .mapValues { it.value.toInt() }
                                .entries
                                .sortedByDescending { it.value }
                                .forEach { entry ->
                                Text(
                                    text = "${formatVitaminName(entry.key)}: ${entry.value}%",
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 10.dp))
                            }

                        }
                    }
                }
                
                item { 
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(text = "Save Meal")
                    }
                }
            }


        }

    }
}






/**
 * the search bar itsself pulled up when adding ingredients
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarMealCreation(query: String, onQueryChanged: (String) -> Unit){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){

        TextField(
            value = query,
            onValueChange = onQueryChanged,
            leadingIcon = {
                Icon(Icons.Rounded.Search,
                    contentDescription = "Search Icon")
            },
            placeholder = { Text(text = "Search")},
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ))



    }
}

/**
 * Search System for the added ingredients
 */

@Composable
fun ListOfSearchedFoodsMealCreation(foodViewModel: FoodViewModel = viewModel(), query: String, onClick: (FoodItem) -> Unit){


    foodViewModel.fetchAllFoodsFromDataManager()
    val allFoods = foodViewModel.allFoods.value
    val filteredFoods = allFoods?.filter { it.name.contains(query, ignoreCase = true)}

    LazyColumn{
        items(items = filteredFoods ?: listOf(), key = {food -> food.id}) {food ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable { onClick(food) }
            ){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(colorResource(id = R.color.white), RoundedCornerShape(8.dp)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    NetworkImage(url = food.imageURL)
                    Text(
                        text = food.name,
                        fontWeight = FontWeight.SemiBold)
                    TierBoxFoodList(tier = food.tier)
                }


            }
        }
    }
}

/**
 * retrieves the image to display with the food
 */


@Composable
fun NetworkImageMealIngredient(url: String){
    /*
    this is used to display iamges that we first get from firebase
     */
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = url)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(R.drawable.round_keyboard_arrow_left_24)
            }).build()
    )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(15)),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center)
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMealCreationScreen(){
    //MealCreationScreen(navController = rememberNavController())
    //IngredientTweaking()
}


/**
 * The Increase and Decrease system for the ingredients once added to the
 */

@Composable
fun IngredientTweaking(

    quantity: Double,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(colorResource(id = R.color.green_c), shape = RoundedCornerShape(15))
    ){
        Text(
            text = "-",
            modifier = Modifier
                .clickable(onClick = onDecrease)
                .padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "+",
            modifier = Modifier
                .clickable(onClick = onIncrease)
                .padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}