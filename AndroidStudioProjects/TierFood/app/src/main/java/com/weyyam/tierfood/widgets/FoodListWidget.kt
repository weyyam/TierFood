package com.weyyam.tierfood.widgets

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weyyam.tierfood.FoodItem
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.R


@Composable
fun FoodListWidget(viewModel: FoodViewModel = viewModel()){
    val foodList = viewModel.foodList

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
    Text(text = food.name)
    NetworkImage(url = food.imageURL)
}

@Composable
fun NetworkImage(url: String){
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
fun FoodCategoriesGrid(onCategorySelected: (String) -> Unit){
    val categories = listOf("Fruit", "Vegetable",)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        content = {
            itemsIndexed(categories){index, category ->
                CategoryIcon(category = category, onClick = onCategorySelected)
            }
        },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.SpaceEvenly
    )
}

@Composable
fun CategoryIcon(category: String, onClick: (String) -> Unit){

    Box(
        modifier = Modifier
            .size(125.dp)
            .clickable { onClick(category) }
            .padding(8.dp)

    ){
        Image(
            painter = painterResource(id = R.drawable.fruit_category),
            contentDescription = "The image for the category",
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.FillBounds)
        Text(
            text = category,
            textAlign = TextAlign.Right)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCategoryIcon(){
    CategoryIcon(category = "Fruit", onClick = {

    })
}