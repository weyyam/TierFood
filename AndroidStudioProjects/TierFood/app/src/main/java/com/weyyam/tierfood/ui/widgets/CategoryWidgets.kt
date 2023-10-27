package com.weyyam.tierfood.ui.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.weyyam.tierfood.navigation.FoodsList
import com.weyyam.tierfood.R
import com.weyyam.tierfood.model.foodCategories

@Composable
fun FoodCategoriesGrid(navController: NavController){
    /**
     * This is the grid layout for the Categories in the search page
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
                        Log.i("TESTING", "the category is:${selectedCategory}/with route:${FoodsList.route}")
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
    /**
     * CategoryIcon
     * @param category The category associated with the Icon
     * @param imageResId The ImageId for said image
     * @param onClick The category associated with the Icon is returned when clicked
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
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(colorResource(id = R.color.background_SecondaryL).copy(alpha = 0.6f)))
    }
}