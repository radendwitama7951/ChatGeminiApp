package com.example.chatgeminiapp.presentation.gemini_chat.chats.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp._common.utils.uriToBitmap
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelector(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,

    bitmapDisplay: Bitmap?,
    onDismiss: () -> Unit = { },
    onResultBitmap: (Bitmap?) -> Unit = {},
    onResultUri: (Uri) -> Unit = {},
    onCancelSelection: () -> Unit = {},
) {
    val imageCropper = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            if (result.isSuccessful) {
                result.uriContent?.let { uri ->
                    onResultBitmap(uriToBitmap(context, uri))
                    onResultUri(uri)
                }
            } else {
                val exception = result.error
                Log.d("test", "Error $exception")
            }
        },
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        ModalBottomSheet(
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
                    onDone = onDismiss, 
                    onSelect = {
                        val cropOptions = CropImageContractOptions(
                            null,
                            CropImageOptions()
                        )
                        imageCropper.launch(cropOptions)
                    },
                    onRemove = onCancelSelection
                )
                Spacer(modifier = modifier.height(smallPadding2))
                ImagePreview(bitmap = bitmapDisplay)

            }

        }
    }
}

@Composable
private fun ImageSelectorAction(
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
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
                MaterialTheme.colorScheme.secondary.copy(alpha = .5f),
                contentColor = MaterialTheme.colorScheme.onSecondary
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
                onClick = onDone
            ) {
                Icon(
                    modifier = modifier
                        .size(mediumIconButton2),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Done",
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
