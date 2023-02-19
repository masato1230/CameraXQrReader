package com.example.cameraxqrreader

import android.util.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun QrCodeOverlay(
    qrCode: Barcode,
    imageSize: Size = Constants.IMAGE_SIZE,
) {
    val qrBoundingBox = qrCode.boundingBox ?: return

    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    with(LocalDensity.current) {
        val scaleFactor = (screenHeightDp * density) / imageSize.height
        val offsetX = (screenWidthDp - screenHeightDp * imageSize.width / imageSize.height) / 2 * density

        val qrCodeTopLeft = Offset(
            x = (qrBoundingBox.left.toFloat() + offsetX) * scaleFactor,
            y = qrBoundingBox.top.toFloat() * scaleFactor,
        )

        Canvas(modifier = Modifier.fillMaxSize()) {

            drawRect(
                color = Color.White,
                topLeft = qrCodeTopLeft,
                size = androidx.compose.ui.geometry.Size(
                    width = (qrBoundingBox.right - qrBoundingBox.left) * scaleFactor,
                    height = (qrBoundingBox.bottom - qrBoundingBox.top) * scaleFactor,
                ),
                style = Stroke(width = 10f),
            )
        }
    }
}