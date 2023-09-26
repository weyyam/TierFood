package com.weyyam.tierfood.ui.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.data.FoodItem
import com.weyyam.tierfood.R


@Composable
fun FoodsListScreen(viewModel: FoodViewModel = viewModel(), category: String){
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
    if (foodList != null){
        LazyColumn{
            items(foodList){food ->
                FoodItemRow(food)
            }
        }
    } else {
        Text(text = "Loading...")
    }
}




@Composable
fun FoodItemRow(food: FoodItem){
    /*
    this is used to make the row for each individual food,
    this needs to be made to look pretty before finish
     */
    Text(text = food.name)
    NetworkImage(url = food.imageURL)
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
            .size(50.dp, 50.dp)
            .clip(RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop)
}



@Composable
@Preview(showBackground = true)
fun PreviewCategoryIcon(){
    CategoryIcon(category = "Fruits", imageResId = R.drawable.fruit_category,onClick = {

    })
}