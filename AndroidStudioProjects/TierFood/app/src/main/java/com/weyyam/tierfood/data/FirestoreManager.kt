package com.weyyam.tierfood.data

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreManager {
    @SuppressLint("StaticFieldLeak")
    val db = Firebase.firestore
}
