package com.weyyam.tierfood

import android.util.Log
import com.weyyam.tierfood.FirestoreManager.db

class DataManager {
    //this returns a list of all food items in the "foods" collection
    fun fetchAllFoods(success: (List<FoodItem>) -> Unit, failure: (Exception) -> Unit){
        db.collection("foods")
            .get()
            .addOnSuccessListener { result ->
                val foods = result.map {
                    FoodItem(
                        it.id,
                        it.data["name"] as String,
                        it.data["description"] as String,
                        it.data["tier"] as String,
                        it.data["imageURL"] as String,
                        it.data["type"] as String) }
                success(foods)
                Log.w("db", "database fetched all food documents")
            }
            .addOnFailureListener{ exception ->
                failure(exception)
                Log.w("db", "Error getting documents", exception)
            }
    }

    //this returns a sinle food item based on the provided document ID
    fun fetchFoodById(documentId: String, success: (FoodItem) -> Unit, failure: (Exception) -> Unit){
        db.collection("foods").document(documentId)
            .get()
            .addOnSuccessListener{document ->
                if (document != null){
                    val food = FoodItem(
                        document.id,
                        document.data?.get("name") as String,
                        document.data?.get("description") as String,
                        document.data?.get("tier") as String,
                        document.data?.get("imageURL") as String,
                        document.data?.get("type") as String)
                    success(food)
                }
            }
            .addOnFailureListener { exception ->
                failure(exception)
            }
    }

    fun fetchFoodsByCategory(category: String?, success: (List<FoodItem>) -> Unit, failure: (Exception) -> Unit){
        Log.i("FL", "fetchFoodsByCategory the category is $category")
        val query = if (category != null){
            db.collection("foods").whereEqualTo("type", category)
        } else {
            db.collection("foods")
        }

        query.get()
            .addOnSuccessListener { result ->
                val foods = result.map {
                    FoodItem(
                        it.id,
                        it.data["name"] as String,
                        it.data["description"] as String,
                        it.data["tier"] as String,
                        it.data["imageURL"] as String,
                        it.data["type"] as String
                    )
                }
                success(foods)
                Log.w("db", "database fetched food documents for category: $category")
            }
            .addOnFailureListener { exception ->
                failure(exception)
                Log.w("db", "Error getting documents for category: $category", exception)
            }
    }
}

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val tier: String,
    val imageURL: String,
    val type: String
)