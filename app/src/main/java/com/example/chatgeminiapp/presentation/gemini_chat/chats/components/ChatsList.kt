package com.example.chatgeminiapp.presentation.gemini_chat.chats.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatgeminiapp.domain.models.gemini_chat.LocalChat
import com.example.chatgeminiapp.presentation.gemini_chat.chats.ChatsState

@Composable
fun ChatsList (
    modifier: Modifier = Modifier,
    chatList: List<LocalChat>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        reverseLayout = true
    ) {
        itemsIndexed(chatList) { index, chat ->
            if (chat.isFromUser) {
                UserChatItem(
                    prompt = chat.prompt, bitmap = chat.bitmap
                )
            } else {
                ModelChatItem(response = chat.prompt)
            }
        }
    }

}