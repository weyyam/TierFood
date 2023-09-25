package com.weyyam.tierfood.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weyyam.tierfood.FoodItem
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.FoodsList
import com.weyyam.tierfood.R
import com.weyyam.tierfood.foodCategories


@Composable
fun FoodsListScreen(viewModel: FoodViewModel = viewModel(), category: String){
    /*
    This is used to to display foods based on the category selected,
    uses view model to fetch the foods then uses a lazy column to display it
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
fun FoodCategoriesGrid(navController: NavController){
    /*
    This is the grid layout for the categories,
    when one is clicked it navigates to the foodsListScreen
     */
    val categories = foodCategories

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        content = {
            itemsIndexed(categories){index, category ->
                CategoryIcon(
                    category = category.name,
                    imageResId = category.imageResId,
                    onClick = {selectedCategory ->
                        navController.navigate(FoodsList.route.replace("{category}", selectedCategory))
                    })
            }
        },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.SpaceEvenly,
        userScrollEnabled = true
    )
}

@Composable
fun CategoryIcon(category: String, imageResId: Int, onClick: (String) -> Unit){
    /*
    This Composable is used to create the icon,
    takes image from drawable and suits it to the category acordingly,
    gets category info from FoodCategory.kt,
    takes a onClick
     */
    Box(
        modifier = Modifier
            .size(125.dp)
            .clickable { onClick(category) }
            .padding(8.dp)

    ){
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "The image for the $category",
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.FillBounds)
        Text(
            text = category,
            fontSize = 26.sp,
            color = colorResource(id = R.color.white),
            modifier = Modifier.align(Alignment.BottomStart))
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCategoryIcon(){
    CategoryIcon(category = "Fruits", imageResId = R.drawable.fruit_category,onClick = {

    })
}