package com.weyyam.tierfood.widgets

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.weyyam.tierfood.DataManager
import com.weyyam.tierfood.FoodItem

class FoodViewModel : ViewModel() {
    private val dataManager = DataManager()

    var foodList by mutableStateOf<List<FoodItem>?>(null)

    init {
        fetchAllFoods()
    }

    private fun fetchAllFoods(){
        dataManager.fetchAllFoods(
            success = { foods ->
                foodList = foods
            },
            failure = { exception ->
                Log.d("db", "error using fetchallfoods $exception")
            }
        )
    }

}
