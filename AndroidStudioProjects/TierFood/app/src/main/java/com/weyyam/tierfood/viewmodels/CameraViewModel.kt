package com.weyyam.tierfood.viewmodels

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weyyam.tierfood.R
import com.weyyam.tierfood.ui.favorite.UserFavoritesManager
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService

class CameraViewModel: ViewModel() {

    private val _capturedImageUri = MutableLiveData<Uri?>()

    val shouldShowCamera = mutableStateOf(false)
    val capturedImageUri: LiveData<Uri?> = _capturedImageUri

    fun toggleCamera(show: Boolean){
        shouldShowCamera.value = show
    }

    fun onImageCaptured(uri: Uri){
        viewModelScope.launch {
            _capturedImageUri.value = uri
        }
    }

    fun resetImage(){
        _capturedImageUri.value = null
    }



}


