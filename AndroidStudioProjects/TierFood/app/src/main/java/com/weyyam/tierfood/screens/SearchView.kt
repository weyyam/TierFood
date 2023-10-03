package com.weyyam.tierfood.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.R
import com.weyyam.tierfood.ui.navbars.BottomBarAppView
import com.weyyam.tierfood.ui.navbars.TopBarAppView
import com.weyyam.tierfood.ui.widgets.FoodCategoriesGrid
import com.weyyam.tierfood.ui.widgets.NetworkImage
import com.weyyam.tierfood.ui.widgets.TierBoxFoodList
import com.weyyam.tierfood.viewmodels.FoodViewModel


@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("")}
    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.background_SecondaryL))) {
        TopBarAppView(navController = navController)
        Box(modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                )
            {
                SearchBar(query = query, onQueryChanged = {query = it})
                if(query.isNotEmpty()){
                    ListOfSearchedFoods(query = query)
                } else {
                    FoodCategoriesGrid(navController = navController)
                }

            }
        }
        Spacer(modifier = Modifier.weight(0.005f))
        BottomBarAppView(navController = navController)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){

        TextField(
            value = query,
            onValueChange = onQueryChanged,
            leadingIcon = {
                Icon(Icons.Default.Search,
                    contentDescription = "Search Icon")
            },
            placeholder = { Text(text = "Search")},
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            ))



    }
}


@Composable
fun ListOfSearchedFoods(viewModel: FoodViewModel = viewModel(), query: String){


    viewModel.fetchAllFoodsFromDataManager()
    val allFoods = viewModel.allFoods.value
    val filteredFoods = allFoods?.filter { it.name.contains(query, ignoreCase = true)}

    LazyColumn{
        items(items = filteredFoods ?: listOf(), key = {food -> food.id}) {food ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    NetworkImage(url = food.imageURL)
                    Text(
                        text = food.name,
                        fontWeight = FontWeight.SemiBold)
                    TierBoxFoodList(tier = food.tier)
                }


            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SearchScreenPreview(){
    SearchScreen(navController = rememberNavController())
}