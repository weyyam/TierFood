package com.weyyam.tierfood.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weyyam.tierfood.data.DataManager
import com.weyyam.tierfood.data.FoodItem

class FoodViewModel : ViewModel() {
    private val dataManager = DataManager()
    private val _allFoods = MutableLiveData<List<FoodItem>>()
    val allFoods: LiveData<List<FoodItem>> get() = _allFoods

    var foodList by mutableStateOf<List<FoodItem>?>(null)
    var category by mutableStateOf<String?>(null)
    var selectedFood by mutableStateOf<FoodItem?>(null)

    init {
        fetchFoodsForCategory(category)
        fetchAllFoodsFromDataManager()
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

    fun fetchAllFoodsFromDataManager() {
        dataManager.fetchAllFoods(
            success = { foods ->
                _allFoods.value = foods
            },
            failure = {exception ->
                Log.e("fetchAllFoods", "No foods to fetch $exception")
            }
        )
    }

    fun searchFoods(query: String): List<FoodItem> {
        return _allFoods.value?.filter {
            it.name.contains(query, ignoreCase = true)
        } ?: listOf()
    }

}
