package com.weyyam.tierfood.ui.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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


@Composable
fun FoodsListScreen(viewModel: FoodViewModel = viewModel(), category: String, navController: NavController){
    /**
     * This is the List for FoodItems when the category is selected
     * @param viewModel The Viewmodel is responsible for managing and providing food
     * data. It handles data operations and communicates the results to the UI.
     * @param category the catigory of food to display
     */
    viewModel.fetchFoodsForCategory(category)
    val foodList = viewModel.foodList
    Log.d("FL","FoodsListScreen runs after composeable calls ")
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background_SecondaryL))) {
        
    }
    if (foodList != null) {
        LazyColumn (
            modifier = Modifier.padding(8.dp)
                ){
            items(foodList) { food ->
                FoodItemRow(
                    food = food,
                    onClick = {selectedFood ->
                        Log.i("FP", "The foodprofile/food.id is:${FoodProfile.route}/${selectedFood.id}")
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




@Composable
fun FoodItemRow(food: FoodItem, onClick: (FoodItem) -> Unit){
    /*
    this is used to make the row for each individual food,
     */
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(food)}
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



@Composable
@Preview(showBackground = true)
fun PreviewFoodItemRow(){
    //FoodItemRow(food = FoodItem(id = "0", name = "Bluebarries", description = "This is the description", tier = "B", type = "Fruits", imageURL = "https://firebasestorage.googleapis.com/v0/b/tierfood-d1dd0.appspot.com/o/BlueBerries_image.png?alt=media&token=4b532482-fabd-47dc-84ad-8c8e9d3112b6"))
}