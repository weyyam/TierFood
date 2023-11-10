package com.weyyam.tierfood.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.weyyam.tierfood.R
import com.weyyam.tierfood.data.loadNutrients
import com.weyyam.tierfood.ui.navbars.BottomBarAppView
import com.weyyam.tierfood.ui.navbars.TopBarAppView

@Composable
fun NutrientProfileScreen(navController: NavController, nutrient: String){
    Log.d("NPS", "NutrientProfileScreen is running")
    val context = LocalContext.current

    val allNutrients = loadNutrients(context)
    val nutrient = allNutrients.find { it.name == nutrient}
    Log.d("NPS", "The Nutrient opening on the NPS is: $nutrient")
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBarAppView(navController = navController, modifier = Modifier)},
        bottomBar = { BottomBarAppView(navController = navController, modifier = Modifier)},
        containerColor = colorResource(id = R.color.background_SecondaryL),
        contentColor = colorResource(id = R.color.black)
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.background_SecondaryL)
            )
            .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (nutrient != null){
                when (nutrient.name) {
                    "SF" -> {
                        DynamicNameTitleSize(text = "Saturated Fat (${nutrient.name})")
                    }

                    "MUF" -> {
                        DynamicNameTitleSize(text = "Monounsaturated Fat (${nutrient.name})")
                    }

                    "PUF" -> {
                        DynamicNameTitleSize(text = "Polyunsaturated Fat (${nutrient.name})")
                    }

                    "GI" -> {
                        DynamicNameTitleSize(text = "Glycemic Index (${nutrient.name})")
                    }

                    else -> {
                        DynamicNameTitleSize(text = nutrient.name)
                    }
                }
                Text(text = "Description",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp),
                    fontSize = 20.sp)
                Text(text = nutrient.description,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 16.sp
                )
                Text(text = nutrient.importance,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                    fontSize = 16.sp
                )
                Text(text = "Recommended Intake",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp,))
                Text(text = nutrient.recommendedIntake,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp,)
                )
                if (nutrient.name == "Calories"){
                    Text(text = "Calories are found in all foods, different Macro nutrients have different amounts of calories:",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 16.sp
                    )
                    Text(text = "Proteins have 4 Calories per gram",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp, end = 12.dp),
                        fontSize = 16.sp)
                    Text(text = "Carbohydrates have 4 Calories per gram",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp, end = 12.dp),
                        fontSize = 16.sp)
                    Text(text = "Fats have 9 Calories per gram",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp, end = 12.dp, bottom = 8.dp),
                        fontSize = 16.sp)
                } else {
                    Text(text = "${nutrient.name} is often found in the following foods:",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        fontSize = 20.sp
                    )
                    Column{
                        nutrient.commonlyFound.forEach { food ->
                            Text(text = food,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp),
                                fontSize = 16.sp
                            )
                        }
                    }
                }

            } else {
                Text(text = "This nutrients seems to be missing, let us know what it is using the feedback feature top right 'i'",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }
}

@Composable
fun DynamicNameTitleSize(text: String){
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        val constraints = this.constraints
        val calculateSize = (constraints.maxWidth / 40).toFloat()
        val minSize = 12f
        val maxSize = 36f
        val textSize = calculateSize.coerceIn(minSize, maxSize).sp
        Log.d("NPStext", "Size of the text is $textSize, and calculated size is $calculateSize")
        Text(
            text = text,
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier)
    }
}