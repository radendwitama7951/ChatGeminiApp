package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.example.chatgeminiapp.domain.usecases.gemini_chat.GetBasicChatResponse
import com.example.chatgeminiapp.domain.usecases.gemini_chat.GetChatResponseWithImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getResponseUseCase: GetBasicChatResponse,
    private val getWithImageResponseUseCase: GetChatResponseWithImage,
) : ViewModel() {
    var state: ChatsState by mutableStateOf(ChatsState())
        private set

    fun onEvent(event: ChatsEvent) {
        when (event) {
            is ChatsEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(event.prompt, event.bitmap)
                    if (event.bitmap != null) {
                        getResponseImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }
                }

            }

            is ChatsEvent.UpdatePrompt -> {
                state = state.copy(prompt = event.newPrompt)
            }

            is ChatsEvent.ResetBitmapUri -> {
                state = state.copy(bitmapUri = "")
            }

            is ChatsEvent.UpdateBitmapUri -> {
                state = state.copy(bitmapUri = event.uri)
            }

            is ChatsEvent.UpdateBitmap -> {
                state = state.copy(bitmap = event.bitmap)
            }
            is ChatsEvent.ResetBitmap -> {
                state.bitmap?.recycle()
                state = state.copy(bitmap = null)
            }

        }
    }

    private fun updateBitmapState (bitmap: Bitmap?) {
        state = state.copy(bitmap = bitmap)
    }


    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        state = state.copy(
            chatList = state.chatList.toMutableList().apply {
                    add(
                        0,
                        LocalChat(
                            prompt = prompt,
                            bitmap = bitmap,
                            isFromUser = true
                        )
                    )
            },
            prompt = "",
            bitmap = null
        )
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = getResponseUseCase(prompt)
            Log.d("test", "Chat History ${state.chatList}")
            state = state.copy(chatList = state.chatList.toMutableList().apply { add(0, chat) })
        }
    }

    private fun getResponseImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val chat = getWithImageResponseUseCase(prompt, bitmap)
            state = state.copy(chatList = state.chatList.toMutableList().apply { add(0, chat) })
        }
    }

}

