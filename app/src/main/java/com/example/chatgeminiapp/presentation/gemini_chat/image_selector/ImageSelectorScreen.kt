package com.example.chatgeminiapp.presentation.gemini_chat.image_selector

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import com.example.chatgeminiapp.presentation.gemini_chat.image_selector.components.ImagePickerButton
import com.example.chatgeminiapp.presentation.gemini_chat.image_selector.components.ImagePreview

import kotlinx.coroutines.launch


@Composable
fun ImageSelectorScreen(
    modifier: Modifier = Modifier,
    state: ImageSelectorState,
    event: (ImageSelectorEvent) -> Unit,

    onFinished: (Bitmap?) -> Unit = {}
) {
    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row (
            modifier = modifier
                .fillMaxHeight(.5f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ImagePreview(uri = state.bitmapUri)
        }
        Row (
            modifier = modifier
                .fillMaxHeight(.5f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ImagePickerButton(
                onPickResultUri = {uri ->
                    event(ImageSelectorEvent.UpdateBitmapUri(uri.toString()))
                },
                onPickResultBitmap = {bitmap ->
                    onFinished(bitmap)
                }
            )
        }

    }
}

//    val imagePicker =
//        rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.PickVisualMedia(),
//            onResult = { uri ->
//                uri?.let {
//                    val cropOptions = CropImageContractOptions(state.bitmapUri.toUri(), CropImageOptions())
//                    imageCropper.launch(cropOptions)
//                }
//            }
//        )
