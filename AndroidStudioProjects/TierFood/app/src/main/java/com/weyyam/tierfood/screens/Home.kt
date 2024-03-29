package com.weyyam.tierfood.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.R
import com.weyyam.tierfood.data.DataManager
import com.weyyam.tierfood.ui.navbars.BottomBarAppView
import com.weyyam.tierfood.ui.navbars.TopBarAppView
import com.weyyam.tierfood.ui.widgets.FoodOfDayCard
import com.weyyam.tierfood.viewmodels.HomeViewModel
import com.weyyam.tierfood.ui.widgets.TiersColumn


@Composable
fun HomeScreen(navController: NavController, userId : String){

    val viewModel: HomeViewModel = viewModel()
    val favoriteFoods by viewModel.favoriteFoods.collectAsState()
    val _loadingState by viewModel.loadingState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchFavoriteFoods(userId = userId)
    }



    BoxWithConstraints {
        val maxHeight = constraints.maxHeight

        Column(modifier = Modifier.fillMaxSize()) {
            TopBarAppView(navController = navController, modifier = Modifier)
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.background_PrimaryD))
                ) {
                    FoodOfDayCard(navController = navController)

                    when (_loadingState) {
                        DataManager.LoadingState.LOADING -> {
                            CircularProgressIndicator()
                        }

                        DataManager.LoadingState.SUCCESS -> {
                            Log.d("FFF", "Loading state success ")
                            Text(
                                text = "These are your saved Foods!",
                                color = colorResource(id = R.color.white),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(colorResource(id = R.color.background_PrimaryD)),
                                textAlign = TextAlign.Center
                            )
                            TiersColumn(favoriteFoods, navController = navController)

                        }

                        DataManager.LoadingState.ERROR -> {
                            Log.e("FFF", "Error regarding the FFF")
                        }

                    }

                }
            }
            BottomBarAppView(navController = navController, modifier = Modifier)
        }
    }

}

