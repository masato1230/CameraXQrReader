package com.example.cameraxqrreader

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.cameraxqrreader.ui.theme.CameraXQrReaderTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraXQrReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var isCameraGranted by remember { mutableStateOf(false) }

                    if (isCameraGranted) {
                        CameraPreview(
                            modifier = Modifier.fillMaxSize(),
                            viewModel.qrCodeAnalyzeUseCase,
                        )
                    } else {
                        RequestCameraPermission { isCameraGranted = true }
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
