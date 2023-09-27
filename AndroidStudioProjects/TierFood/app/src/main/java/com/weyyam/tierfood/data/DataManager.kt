package com.weyyam.tierfood.data

import android.util.Log
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.weyyam.tierfood.data.FirestoreManager.db


/**
 * manager responsible for fetching food data
 */
class DataManager {

    /**
     * Fetches all food Items
     * @param success Callback for successful fetch
     * @param failure Callback for fetch failure
     */
    fun fetchAllFoods(success: (List<FoodItem>) -> Unit, failure: (Exception) -> Unit){
        db.collection("foods")
            .get()
            .addOnSuccessListener { result ->
                val foods = result.map { it.toFoodItem() }
                success(foods)
                Log.w("DataManager", "database fetched all food documents")
            }
            .addOnFailureListener{ exception ->
                failure(exception)
                Log.w("DataManager", "Error getting documents", exception)
            }
    }


    /**
     * Fetches a food Item by its documented ID.
     * @param documentId The ID of the Food document
     * @param success Callback for successful fetch
     * @param failure Callback for fetch failure
     */

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

    /**
     * Fetches food items based on category
     * @param category The category of foods to fetch
     * @param success Callback for successful fetch
     * @param failure Callback for fetch failure
     */

    fun fetchFoodsByCategory(category: String?, success: (List<FoodItem>) -> Unit, failure: (Exception) -> Unit){
        Log.i("FL", "fetchFoodsByCategory the category is $category")
        val query = if (category != null){
            db.collection("foods").whereEqualTo("type", category)
        } else {
            db.collection("foods")
        }

        query.get()
            .addOnSuccessListener { result ->
                val foods = result.map {it.toFoodItem() }
                success(foods)
                Log.w("db", "database fetched food documents for category: $category")
            }
            .addOnFailureListener { exception ->
                failure(exception)
                Log.w("db", "Error getting documents for category: $category", exception)
            }
    }

    fun fetchFoodByName(name : String?, success: (FoodItem) -> Unit, failure: (Exception) -> Unit){
        if (name == null){
            failure(Exception("Name cannot be null"))
            return
        }

        db.collection("foods")
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener { result ->
                val foodItems = result.map { it.toFoodItem()}
                if (foodItems.isNotEmpty()){
                    success(foodItems.first())
                    Log.w("DataManager", "database fetched food document with name: $name")
                }else{
                    failure(Exception("No food found with name: $name"))
                }
            }
            .addOnFailureListener { exception ->
                failure(exception)
                Log.w("DataManager", "Error getting document with name: $name", exception)
            }
    }

    private fun QueryDocumentSnapshot.toFoodItem(): FoodItem {
        return FoodItem(
            id = id,
            name = data["name"] as String,
            description = data["description"] as String,
            tier = data["tier"] as String,
            imageURL = data["imageURL"] as String,
            type = data["type"] as String
        )
    }
}