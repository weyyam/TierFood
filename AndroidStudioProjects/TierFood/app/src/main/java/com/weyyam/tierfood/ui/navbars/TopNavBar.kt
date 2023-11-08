package com.weyyam.tierfood.ui.navbars

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.weyyam.tierfood.R
import com.weyyam.tierfood.ui.feedback.FeedbackDialog

@Composable
fun TopBarAppView(navController: NavController, modifier: Modifier){

    val showDialog = remember { mutableStateOf(false)}
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = remember { mutableStateOf("")}
    val showSnackbar = remember { mutableStateOf(false)}
    Row(
        modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.background_PrimaryD)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add food item icon")

        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.logo_icon_white),
            contentDescription = "Temp Logo for the top nav bar",
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Fit)

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { showDialog.value = true }) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Feedback Button")

        }

        LaunchedEffect(showSnackbar.value){
            Log.d("FEEDBACK", "Launchedeffect has run with showSnackbar: ${showSnackbar.value}")
            if (showSnackbar.value){
                snackbarHostState.showSnackbar(
                    message = snackbarMessage.value,
                    duration = SnackbarDuration.Short
                )
                showSnackbar.value = false
            }
        }

    }
    SnackbarHost(hostState = snackbarHostState)
    FeedbackDialog(showDialog = showDialog) { feedback, category ->

        Log.d("FEEDBACK", "feedback dialog")

        val feedbackRef = Firebase.firestore.collection("feedback").document()
        val feedbackData = mapOf(
            "feedbackText" to feedback,
        "category" to category.displayName,
        "timestamp" to System.currentTimeMillis())
        feedbackRef.set(feedbackData)
            .addOnSuccessListener {
                snackbarMessage.value = "Feedback submitted successfully, Thank You!"
                showSnackbar.value = true
            }
            .addOnFailureListener {exception ->
                snackbarMessage.value = "Error submitting feedback: ${exception.localizedMessage}"
                showSnackbar.value = true
            }

    }
}

