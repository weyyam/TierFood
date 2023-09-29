package com.weyyam.tierfood.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.weyyam.tierfood.data.DataManager
import com.weyyam.tierfood.data.FoodItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FoodOfDayViewModel : ViewModel() {
    private val dataManager = DataManager()

    var foodOfTheDay by mutableStateOf<FoodItem?>(null)

    init {
        fetchFoodOfTheDay()
    }

    private fun fetchFoodOfTheDay(){
        dataManager.fetchAllFoods(
            success = {foods ->
                foodOfTheDay = getFoodOfTheDay(foods)
            },
            failure = {exception ->
                Log.d("db", "Error fetching food of the day $exception")
            }
        )
    }

    private fun getFoodOfTheDay(foods: List<FoodItem>): FoodItem {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val hashValue = today.hashCode()
        Log.i("fodc", "${foods.size}")
        val index = kotlin.math.abs(hashValue) % foods.size
        Log.i("fodc", "$index")
        return foods[index]
    }
}