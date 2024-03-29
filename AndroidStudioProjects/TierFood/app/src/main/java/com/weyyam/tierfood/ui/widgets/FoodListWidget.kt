package com.weyyam.tierfood.ui.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.R
import com.weyyam.tierfood.data.FoodItem
import com.weyyam.tierfood.navigation.FoodProfile
import com.weyyam.tierfood.screens.SearchBar
import com.weyyam.tierfood.ui.navbars.BottomBarAppView
import com.weyyam.tierfood.ui.navbars.TopBarAppView
import com.weyyam.tierfood.viewmodels.FoodViewModel


@Composable
fun FoodsListScreen(viewModel: FoodViewModel = viewModel(), category: String, navController: NavController) {
    /**
     * This is the List for FoodItems when the category is selected
     * @param viewModel The Viewmodel is responsible for managing and providing food
     * data. It handles data operations and communicates the results to the UI.
     * @param category the catigory of food to display
     */
    viewModel.fetchFoodsForCategory(category)
    val foodList = viewModel.foodList
    var query by remember { mutableStateOf("")}
    var isToggled by remember { mutableStateOf(false)}


    Log.d("FL", "FoodsListScreen runs after composeable calls ")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_SecondaryL))
    ) {
        TopBarAppView(navController = navController, modifier = Modifier)
        SearchBarCat(query = query, onQueryChanged = {query = it} )
        sortBar(tierSortIsToggled = isToggled){ newValue ->
            isToggled = newValue
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_SecondaryL))

        ) {
            Column() {
                if (foodList != null) {
                    Log.d("NAVBAR", "foodlist ifstatement ran")

                    val tierOrder = listOf("S", "A", "B", "C", "D", "F")


                    val filteredAndSortedFoods = foodList
                        .filter { it.name.contains(query, ignoreCase = true) }
                        .sortedWith(compareBy({if (isToggled) tierOrder.indexOf(it.tier) else 0}, { it.name}))

                    LazyColumn(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(filteredAndSortedFoods) { food ->
                            FoodItemRow(
                                food = food,
                                onClick = { selectedFood ->
                                    Log.i(
                                        "FP",
                                        "The foodprofile/food.id is:${FoodProfile.route}/${selectedFood.id}"
                                    )
                                    navController.navigate("${FoodProfile.route}/${selectedFood.id}")
                                    Log.i("FP", "navController.navigate ran successfully ")
                                }
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Divider(color = colorResource(id = R.color.background_PrimaryD))
                            Spacer(modifier = Modifier.padding(4.dp))
                        }
                    }

                } else {
                    Text(text = "Loading...")
                }
            }


        }
        /*
        This doesnt work for some reason should look into this and what makes
        the bottom nav bar stay in one place
         */
        Spacer(modifier = Modifier.weight(1f))
        BottomBarAppView(navController = navController, modifier = Modifier)

    }
}



@Composable
fun FoodItemRow(food: FoodItem, onClick: (FoodItem) -> Unit){
    /*
    this is used to make the row for each individual food,
     */
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(food) }
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

            NetworkImage(url = food.imageURL)
            Text(text = food.name)
            TierBoxFoodList(tier = food.tier)
        }


    }
}

@Composable
fun NetworkImage(url: String){
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
            .size(48.dp)
            .clip(RoundedCornerShape(15)),
        contentScale = ContentScale.Crop)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarCat(query: String, onQueryChanged: (String) -> Unit){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){

        TextField(
            value = query,
            onValueChange = onQueryChanged,
            leadingIcon = {
                Icon(Icons.Default.Search,
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

@Composable
fun sortBar(tierSortIsToggled: Boolean, onToggle: (Boolean) -> Unit){

    val iconColor = if (tierSortIsToggled) colorResource(id = R.color.white) else colorResource(id = R.color.black)

    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.05f)
        .background(color = colorResource(R.color.background_SecondaryL)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 26.dp)
                .alpha(0f))
        Icon(
            Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 26.dp)
                .alpha(0f)
                    )
        Icon(
            Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 26.dp)
                .clickable(onClick = { onToggle(!tierSortIsToggled) }),
            tint = iconColor)
    }
}



@Composable
@Preview(showBackground = true)
fun sortBarPreview(){
    //sortBar()
}