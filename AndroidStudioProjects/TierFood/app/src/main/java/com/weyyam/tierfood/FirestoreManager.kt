package com.weyyam.tierfood

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreManager {
    @SuppressLint("StaticFieldLeak")
    //unsure what this does remove if database stops working
    val db = Firebase.firestore
}
