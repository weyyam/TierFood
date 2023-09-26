package com.weyyam.tierfood.model

import com.weyyam.tierfood.R

data class Tier(
    val letter: String,
    val color: Int,
)



val s_rank = Tier(letter = "S", color = R.color.red_s)
val a_rank = Tier(letter = "A", color = R.color.orange_a)
val b_rank = Tier(letter = "B", color = R.color.yellow_b)
val c_rank = Tier(letter = "C", color = R.color.green_c)
val d_rank = Tier(letter = "D", color = R.color.blue_d)
val f_rank = Tier(letter = "F", color = R.color.purple_f)