package com.example.cameraxqrreader

import androidx.camera.core.ImageAnalysis
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {

    private val _qrCode = mutableStateOf<Barcode?>(null)
    val qrCode: State<Barcode?> = _qrCode

    val qrCodeAnalyzeUseCase = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .setTargetResolution(Constants.IMAGE_SIZE)
        .build()
        .also {
            it.setAnalyzer(
                Executors.newSingleThreadExecutor(),
                QrCodeAnalyzer { qrCode ->
                    _qrCode.value = qrCode
                },
            )
        }
}