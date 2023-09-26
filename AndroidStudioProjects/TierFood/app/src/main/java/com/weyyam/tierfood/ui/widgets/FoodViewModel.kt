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

}
