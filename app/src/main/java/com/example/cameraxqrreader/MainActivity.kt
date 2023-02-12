package com.example.cameraxqrreader

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import android.Manifest
import android.util.Log
import com.example.cameraxqrreader.ui.theme.CameraXQrReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraXQrReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RequestCameraPermission {
                        Log.d("OnPermissionGranted", "Camera")
                    }
                }
            }
        }
    }
}

@Composable
private fun Activity.RequestCameraPermission(
    onPermissionGranted: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (!isGranted) {
            finish()
        } else {
            onPermissionGranted()
        }
    }
    LaunchedEffect(key1 = Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }
}
