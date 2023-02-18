package com.example.cameraxqrreader

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.ViewModel
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {

    val qrCodeAnalyzeUseCase = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also {
            it.setAnalyzer(
                Executors.newSingleThreadExecutor(),
                QrCodeAnalyzer {
                    // TODO
                    Log.d("OnQrDetected", it.toString())
                },
            )
        }
}