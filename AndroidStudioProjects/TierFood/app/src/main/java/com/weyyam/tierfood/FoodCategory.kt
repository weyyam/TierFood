package com.weyyam.tierfood

data class FoodCategory(
    val name: String,
    val imageResId: Int
)

val foodCategories = listOf(
    FoodCategory(name = "Fruits", imageResId = R.drawable.fruit_category),
    FoodCategory(name = "Vegetables", imageResId = R.drawable.vegetable_category )
)
