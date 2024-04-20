package com.example.chatgeminiapp.presentation.gemini_chat.image_selector.components

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.chatgeminiapp._common.utils.uriToBitmap

@Composable
fun ImagePickerButton (
    modifier: Modifier = Modifier,
    text: String = "Pick Image",
    context: Context = LocalContext.current,

    onPickResultUri: (Uri) -> Unit = {},
    onPickResultBitmap: (Bitmap?) -> Unit
) {
    val imageCropper = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = {result ->
            if (result.isSuccessful) {
                result.uriContent?.let {uri ->
                    onPickResultBitmap( uriToBitmap(context, uri) )
                    onPickResultUri(uri)
                }
            } else {
                val exception = result.error
                Log.d("test", "Error $exception")
            }
        },
    )
    Button(
        modifier = modifier,
        onClick = {
            val cropOptions = CropImageContractOptions(
                null,
                CropImageOptions()
            )
            imageCropper.launch(cropOptions)                }
    ) {
        Text(text = text)
    }
}