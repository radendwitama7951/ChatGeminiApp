package com.example.chatgeminiapp.presentation.gemini_chat.image_selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageSelectorViewModel @Inject constructor(): ViewModel() {
    var state: ImageSelectorState by mutableStateOf(ImageSelectorState())
        private set

    fun onEvent (event: ImageSelectorEvent) {
        when (event) {
            is ImageSelectorEvent.UpdateBitmapUri -> {
                state = state.copy(bitmapUri = event.uri)
            }
        }

    }
}