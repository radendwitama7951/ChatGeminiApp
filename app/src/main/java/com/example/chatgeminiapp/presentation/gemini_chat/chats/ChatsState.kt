package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup

data class ChatsState(
    /*
    * Data State
    * */
    val selectedGroup: Long? = null,
    val chatGroups: List<InternalChatGroup> = emptyList<InternalChatGroup>(),
    val chatList: List<InternalChat> = emptyList<InternalChat>(),

    /*
    * Screen State
    * */
    val isRequest: Boolean = false,
    val isLoading: Boolean = false,


    /*
    * Input State
    * */
    val prompt: String = "",
//    val bitmap: Bitmap? = null,
    val bitmapCroppedUri: String? = null,
    val bitmapSourceUri: String? = null
)
