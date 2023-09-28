package com.weyyam.tierfood.ui.widgets

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.weyyam.tierfood.data.DataManager
import com.weyyam.tierfood.data.FoodItem

class FoodViewModel : ViewModel() {
    private val dataManager = DataManager()

    var foodList by mutableStateOf<List<FoodItem>?>(null)
    var category by mutableStateOf<String?>(null)
    var selectedFood by mutableStateOf<FoodItem?>(null)

    init {
        fetchFoodsForCategory(category)
    }

    fun selectCategory(cat: String?){
        category = cat
        fetchFoodsForCategory(cat)
    }

    fun fetchFoodsForCategory(category: String?){
        Log.i("FL", "fetchFoodsForCategory viewmodel is showing $category for category")
        dataManager.fetchFoodsByCategory(
            category = category,
            success = { foods ->
                foodList = foods
            },
            failure = { exception ->
                Log.d("db", "error using fetchFoodsForCategory $category $exception")
            }
        )
    }

    fun getFoodByName(name: String): FoodItem? {
        dataManager.fetchFoodByName(
            name = name,
            success = { food ->
                selectedFood = food
            },
            failure = {exception ->
                Log.d("FoodByName", "Failed to get food by name $exception")
            }
        )
        return selectedFood
    }

    fun getFoodById(Id: String): FoodItem?{
        dataManager.fetchFoodById(
            documentId = Id,
            success = {food ->
                selectedFood = food
            },
            failure = {exception ->
                Log.e("FoodById", "failed to get food by Id $exception")
            }
        )
        return selectedFood
    }

}
