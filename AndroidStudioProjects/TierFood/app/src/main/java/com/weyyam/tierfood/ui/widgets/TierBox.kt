package com.weyyam.tierfood.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weyyam.tierfood.model.a_rank
import com.weyyam.tierfood.model.b_rank
import com.weyyam.tierfood.model.c_rank
import com.weyyam.tierfood.model.d_rank
import com.weyyam.tierfood.model.f_rank
import com.weyyam.tierfood.model.s_rank

@Composable
fun TierBoxFoodList(tier: String){

    val tierRank = tierMap[tier] ?: error("String to tier invalid $tier")

    val tierMap = mapOf(
        "S" to s_rank,
        "A" to a_rank,
        "B" to b_rank,
        "C" to c_rank,
        "D" to d_rank,
        "F" to f_rank
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .size(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = tierRank.color)),
        contentAlignment = Alignment.Center){
        Text(
            text = tierRank.letter,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TierBoxFoodProfile(tier: String){


    val tierRank = tierMap[tier] ?: error("String to tier invalid $tier")

    val tierMap = mapOf(
        "S" to s_rank,
        "A" to a_rank,
        "B" to b_rank,
        "C" to c_rank,
        "D" to d_rank,
        "F" to f_rank
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(75.dp)
            .clip(RoundedCornerShape(15))
            .background(colorResource(id = tierRank.color)),
        contentAlignment = Alignment.Center){
        Text(
            text = tierRank.letter,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
    }
}


@Composable
fun TierBoxMeal(tier: String, modifier: Modifier = Modifier){
    val tierRank = tierMap[tier] ?: error("String to tier invalid $tier")

    Box(
        modifier = modifier
            .padding(6.dp)
            .size(60.dp)
            .clip(RoundedCornerShape(15))
            .background(colorResource(id = tierRank.color)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = tierRank.letter,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TierBoxMealIngredient(tier: String, modifier: Modifier = Modifier){
    val tierRank = tierMap[tier] ?: error("String to tier invalid $tier")

    Box(
        modifier = modifier
            .padding(6.dp)
            .size(30.dp)
            .clip(RoundedCornerShape(15))
            .background(colorResource(id = tierRank.color)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = tierRank.letter,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
    }
}