package com.weyyam.tierfood.model

import com.weyyam.tierfood.R

data class FoodCategory(
    val name: String,
    val imageResId: Int
)

val foodCategories = listOf(
    FoodCategory(name = "Fruits", imageResId = R.drawable.fruits_category),
    FoodCategory(name = "Vegetables", imageResId = R.drawable.vegetable_category ),
    FoodCategory(name = "Meats", imageResId = R.drawable.meats_category),
    FoodCategory(name = "Grains", imageResId = R.drawable.grains_category),
    FoodCategory(name = "Seafoods", imageResId = R.drawable.seafood_category),
    FoodCategory(name = "Nuts", imageResId = R.drawable.nuts_category),
    FoodCategory(name = "Beans", imageResId = R.drawable.beans_category)
)
