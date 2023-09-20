package com.weyyam.tierfood.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weyyam.tierfood.R
import com.weyyam.tierfood.navbars.BottomBarAppView
import com.weyyam.tierfood.navbars.TopBarAppView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {


    Column(modifier = Modifier.fillMaxSize()) {
        TopBarAppView(navController = navController)
        Box(modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_SecondaryL)))
            {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    var query by remember { mutableStateOf("")}
                    TextField(
                        value = query,
                        onValueChange = {query = it},
                        leadingIcon = {
                            Icon(Icons.Default.Search,
                                contentDescription = "Search Icon")
                        },
                        placeholder = { Text(text = "Search")},
                        singleLine = true,
                        shape = RoundedCornerShape(15),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            containerColor = Color.White
                        ))

                }
                Spacer(modifier = Modifier.weight(1f))
                BottomBarAppView(navController = navController)
                //To make it such that the Bar stays on the bottom add lazy list and move BottomBarAppView out of the column


            }
        }
    }





}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview(){
    SearchScreen(navController = rememberNavController())
}