package com.weyyam.tierfood.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.weyyam.tierfood.data.DataManager
import com.weyyam.tierfood.data.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {

    private val dataManager = DataManager()

    private val _loadingState = MutableStateFlow(DataManager.LoadingState.LOADING)
    val loadingState: StateFlow<DataManager.LoadingState> get() = _loadingState

    private val _favoriteFoods = MutableStateFlow<List<FoodItem>>(emptyList())
    val favoriteFoods: StateFlow<List<FoodItem>> get() = _favoriteFoods

    fun fetchFavoriteFoods(userId: String){
        _loadingState.value = DataManager.LoadingState.LOADING

        Log.i("FFF", "launch fetching favorite foods")
        dataManager.fetchFavoriteFoods(
            userId = userId,
            success = {foods ->
                _favoriteFoods.value = foods
                _loadingState.value = DataManager.LoadingState.SUCCESS
            },
            failure = {exception ->
                _loadingState.value = DataManager.LoadingState.ERROR
                Log.d("FFF", "Failure to fetch favorite foods $exception")
            }
        )
    }
}