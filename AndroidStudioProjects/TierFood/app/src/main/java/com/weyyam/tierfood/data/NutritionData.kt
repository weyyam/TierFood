package com.weyyam.tierfood.data

data class Nutrient(
    val name: String,
    val description: String,
    val importance: String,
    val recommendedIntake: String,
    val commonlyFound: Array<String>
)
