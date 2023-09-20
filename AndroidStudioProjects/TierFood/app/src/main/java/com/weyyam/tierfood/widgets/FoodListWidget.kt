package com.weyyam.tierfood.widgets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.weyyam.tierfood.FoodItem
import androidx.lifecycle.viewmodel.compose.viewModel


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
}