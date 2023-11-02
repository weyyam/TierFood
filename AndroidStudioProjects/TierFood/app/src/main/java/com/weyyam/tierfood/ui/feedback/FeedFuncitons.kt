package com.weyyam.tierfood.ui.feedback

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.DialogProperties
import com.weyyam.tierfood.R

enum class FeedbackCategory(val displayName: String) {
    MISSING_ITEM("Missing Food Item"),
    REPORT_BUG("Report Bug"),
    SUGGESTION("Suggestion"),
    OTHER("Other")
}



@Composable
fun FeedbackDialog(showDialog: MutableState<Boolean>, onFeedbackSubmit: (String, FeedbackCategory) -> Unit){
    var feedbackQuery by remember { mutableStateOf("")}
    var selectedCategory by remember {mutableStateOf(FeedbackCategory.SUGGESTION)}
    var expanded by remember { mutableStateOf(false)}
    if(showDialog.value){
        AlertDialog(

            onDismissRequest = { showDialog.value = false},

            title = { Text("Feedback")},
            text = {
                Column() {
                    //add composable with feedback input feild
                    TextField(
                        value = feedbackQuery,
                        onValueChange = { feedbackQuery = it },
                        placeholder = { Text(text = "Leave Feedback")}
                    )

                    Button(onClick = 
                    { expanded = true },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green_c))){
                        Text(selectedCategory.displayName)
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        FeedbackCategory.values().forEach { category ->
                            DropdownMenuItem(
                                { Text(category.displayName) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false },
                                
                            )
                        }
                    }
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        //Collect the feedback data and pass it to onFeedbackSubmit function
                        Log.d("FEEDBACK", "confirmbutton clicked")
                        val feedback = feedbackQuery
                        onFeedbackSubmit(feedback, selectedCategory)
                        Log.d("FEEDBACK", "feedback was submit as $feedback")
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.red_s)
                    )

                ) {
                    Text(text = "Submit")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purple_f)
                    )
                ) {
                    Text(text = "Cancel")
                }
            },
            properties = DialogProperties()
        )
    }
}

