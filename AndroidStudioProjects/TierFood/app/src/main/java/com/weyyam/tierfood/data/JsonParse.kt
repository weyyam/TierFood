package com.weyyam.tierfood.data

import android.content.Context
import com.google.gson.Gson

fun loadNutrients(context: Context): List<Nutrient> {
    val jsonString = context.assets.open("nutrients.json").bufferedReader().use { it.readText() }
    val gson = Gson()
    return gson.fromJson(jsonString, Array<Nutrient>::class.java).toList()
}