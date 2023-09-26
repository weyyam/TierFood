package com.weyyam.tierfood.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weyyam.tierfood.R
import com.weyyam.tierfood.model.Tier
import com.weyyam.tierfood.model.a_rank
import com.weyyam.tierfood.model.b_rank
import com.weyyam.tierfood.model.c_rank
import com.weyyam.tierfood.model.d_rank
import com.weyyam.tierfood.model.f_rank
import com.weyyam.tierfood.model.s_rank

@Composable
fun TierRow(tier: Tier){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(75.dp)
        .background(colorResource(id = R.color.background_SecondaryL)),
        verticalAlignment = Alignment.CenterVertically,


        ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = tier.color)),
            contentAlignment = Alignment.Center){
            Text(
                text = tier.letter,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }
    }
    Divider(color = colorResource(id = R.color.background_PrimaryD), thickness = 1.dp)

}

@Composable
fun TiersColumn(){
    Column() {
        TierRow(tier = s_rank)
        TierRow(tier = a_rank)
        TierRow(tier = b_rank)
        TierRow(tier = c_rank)
        TierRow(tier = d_rank)
        TierRow(tier = f_rank)
    }

}

@Composable
@Preview(showBackground = true)
fun Previewtierrow(){
    TiersColumn()
}