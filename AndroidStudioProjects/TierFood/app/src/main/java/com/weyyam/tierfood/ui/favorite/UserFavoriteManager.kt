package com.weyyam.tierfood.ui.favorite

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

open class UserFavoritesManager(private val userId: String) {

    private val db = FirebaseFirestore.getInstance()
    private val userFavoritesRef = db.collection("userFavorites").document(userId)

    open fun addFavorites(foodId: String, onSuccess: () -> Unit, onFailure: (Exception)-> Unit){
        userFavoritesRef.update("favorites", FieldValue.arrayUnion(foodId))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    open fun removeFavorites(foodId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        userFavoritesRef.update("favorites", FieldValue.arrayRemove(foodId))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    open fun fetchFavorites(onSuccess: (List<String>) -> Unit, onFailure: (Exception) -> Unit){
        userFavoritesRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val favorites = document.get("favorites") as? List<String> ?: listOf()
                    onSuccess(favorites)
                }else{
                    onFailure(Exception("No Such document"))
                }
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    open fun isFavorited(foodId: String, onResult: (Boolean) -> Unit){
        fetchFavorites(
            onSuccess = { favorites ->
                onResult(foodId in favorites)
            },
            onFailure = {
                onResult(false)
            }
        )
    }

    open fun initializeUserFavorites(){
        userFavoritesRef.get()
            .addOnSuccessListener { document ->
                if(document == null || !document.exists()){
                    //user already has list of favorites
                    userFavoritesRef.set(mapOf("favorites" to listOf<String>()))
                    Log.i("Favorites", "database created for favorites")
                }
            }

    }

}

