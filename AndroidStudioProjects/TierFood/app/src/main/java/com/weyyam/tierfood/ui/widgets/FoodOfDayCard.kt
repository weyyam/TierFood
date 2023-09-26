package com.weyyam.tierfood.ui.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.weyyam.tierfood.R
import com.weyyam.tierfood.data.DataManager
import com.weyyam.tierfood.data.FoodItem
import com.weyyam.tierfood.model.a_rank
import com.weyyam.tierfood.model.b_rank
import com.weyyam.tierfood.model.c_rank
import com.weyyam.tierfood.model.d_rank
import com.weyyam.tierfood.model.f_rank
import com.weyyam.tierfood.model.s_rank
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun FoodOfDayCard(){
    
    val viewModel: FoodOfDayViewModel = viewModel()
    
    val foodOfTheDay = viewModel.foodOfTheDay

    if (foodOfTheDay != null){
        fotdc(foodOfTheDay = foodOfTheDay)
    }else{
        CircularProgressIndicator()
    }

    

}

@Composable
fun fotdc(foodOfTheDay: FoodItem){

    val tiervalue = foodOfTheDay.tier
    val tierDataClass = tierMap[tiervalue] ?: error("Invalid tier string $tiervalue")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(colorResource(id = R.color.background_SecondaryL))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center) {
                    Log.i("fodc", "just before painter image request")
                    val painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = foodOfTheDay.imageURL)
                            .apply(block = fun ImageRequest.Builder.(){
                                crossfade(true)
                                placeholder(R.drawable.round_keyboard_arrow_left_24)
                            }).build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = "foodOfTheDay Image",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop)
                }

                Text(text = foodOfTheDay.name)
                // potentially make this its own class at some point
                //This is the S Box
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(id = tierDataClass.color)),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = tierDataClass.letter,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
            Text(text = foodOfTheDay.description)
        }
    } 
}


val tierMap = mapOf(
    "S" to s_rank,
    "A" to a_rank,
    "B" to b_rank,
    "C" to c_rank,
    "D" to d_rank,
    "F" to f_rank
)




@Preview(showBackground = true)
@Composable
fun previewFoodOfDayCard(){
    FoodOfDayCard()
}