package com.weyyam.tierfood.data

/**
 * States all values for the FoodItem dataclass
 */
data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val tier: String,
    val imageURL: String,
    val type: String
)