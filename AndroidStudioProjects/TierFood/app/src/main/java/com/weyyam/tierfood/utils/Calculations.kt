package com.weyyam.tierfood.utils

import android.util.Log
import com.weyyam.tierfood.data.FoodItem

fun calculateMacroNutrients(foodItem: FoodItem, quantity: Double): Map<String, Double>? {
    Log.d("Calculations", "foodItem: ${foodItem}")
    Log.d("Calculations", "foodItem.macros: ${foodItem.macros}")
    val macrosAdjusted = foodItem.macros?.mapValues { (_, value) -> (value * quantity) }
    Log.d("Calculations", "Calculation for macros: $macrosAdjusted")
    return macrosAdjusted
}

fun calculateMicroNutrients(foodItem: FoodItem, quantity: Double): Map<String, Double>? {
    val microsAdjusted = foodItem.micros?.mapValues { (_, value) -> (value * quantity) }
    return microsAdjusted
}