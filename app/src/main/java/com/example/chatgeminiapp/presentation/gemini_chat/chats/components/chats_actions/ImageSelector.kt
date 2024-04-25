package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Keyboard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.chatgeminiapp._common.utils.uriToBitmap
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelector(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
    windowInsets: WindowInsets = WindowInsets.displayCutout,

    bitmapDisplay: String?,
    onDismiss: () -> Unit = { },
    onResultBitmap: (Bitmap?) -> Unit = {},
    onResultUri: (Uri) -> Unit = {},
    onResultBitmapCroppedUri: (Uri) -> Unit = {},
    onCancelSelection: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {

    val cropOptions = CropImageContractOptions(
        null,
        CropImageOptions(
        )
    ).setImageSource(includeCamera = false, includeGallery = true)
    val imageCropper = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            if (result.isSuccessful) {
                result.originalUri?.let { uri ->
                    onResultUri(uri)
                }
                result.uriContent?.let { uri ->
                    onResultBitmap(uriToBitmap(context, uri))
                    onResultBitmapCroppedUri(uri)
                }
            } else {
                val exception = result.error
                Log.d("test", "Error $exception")
            }
        },
    )
    ModalBottomSheet(
        windowInsets = windowInsets,
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        // Sheet content
        Column(
            modifier = modifier
                .fillMaxHeight(.66f)
                .fillMaxWidth()
                .padding(smallPadding2),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageSelectorAction(
                onConfirm = onConfirm,
                onDismiss = onDismiss,
                onSelect = {
                    imageCropper.launch(
                        cropOptions
                    )
                },
                onRemove = onCancelSelection
            )
            Spacer(modifier = modifier.height(smallPadding2))
            ImagePreview(bitmapSourceUri = bitmapDisplay)

        }

    }
}

@Composable
private fun ImageSelectorAction(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onRemove: () -> Unit,
    onSelect: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            IconButton(
                modifier = modifier,
                onClick = onRemove
            ) {
                Icon(
                    modifier = modifier
                        .size(mediumIconButton2),
                    imageVector = Icons.Rounded.DeleteOutline,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                )
            }
        }

        Button(
            modifier = modifier
                .weight(1f),
            shape = RoundedCornerShape(smallPadding2),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = onSelect
        ) {
            Text(
                text = "Pick Image",
            )
        }
        Box(modifier = modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            IconButton(
                modifier = modifier,
                onClick = {
                    onDismiss()
                    onConfirm()
                }
            ) {
                Icon(
                    modifier = modifier
                        .size(mediumIconButton2),
                    imageVector = Icons.Rounded.Keyboard,
                    contentDescription = "Done selecting image, continue with the prompt",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                )
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//fun ImageSelectorPreview () {
//    ChatGeminiAppTheme (dynamicColor = false) {
//        Surface (modifier = Modifier.fillMaxSize()) {
//            ImageSelector()
//        }
//    }
//
//}
//
