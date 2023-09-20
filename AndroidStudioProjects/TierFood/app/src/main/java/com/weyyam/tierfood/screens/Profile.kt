package com.weyyam.tierfood.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.weyyam.tierfood.R
import com.weyyam.tierfood.navbars.BottomBarAppView
import com.weyyam.tierfood.navbars.TopBarAppView
import com.weyyam.tierfood.sign_in.UserData

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userData: UserData?,
    onSignOut: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_SecondaryL))

    ) {
        TopBarAppView(navController = navController)
        Box(
            modifier = Modifier.weight(0.5f)
        ){
            Column(
                modifier =Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = "You are in the Profile")
                if(userData?.profilePictureUrl != null){
                    AsyncImage(model = userData.profilePictureUrl,
                        contentDescription ="Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)
                }
                if(userData?.username != null){
                    Text(
                        text = userData.username,
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold)
                }
                Button(onClick = {
                    onSignOut()
                    Log.d("SignIn", "Signout pressed")
                }) {
                    Text(text = "SignOut")
                }


            }

        }
        Box(
            modifier = Modifier.weight(0.5f)
        ){
            Column() {

                Spacer(modifier = Modifier.weight(1f))
            }
        }
        BottomBarAppView(navController = navController)
        //To make it such that the Bar stays on the bottom add lazy list and move BottomBarAppView out of the column

    }

    
}

@Composable
@Preview(showBackground = true)
fun previewProfile(){
    ProfileScreen(
        navController = rememberNavController(),
        onSignOut = {},
    userData = null)
}