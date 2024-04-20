package com.example.chatgeminiapp.domain.models.gemini_chat

import androidx.room.Embedded
import androidx.room.Relation

data class ChatGroupWithChatItems(
    @Embedded val prop: ChatGroup,
    @Relation(
        parentColumn = "id",
        entityColumn = "chatGroupOwner"
    )
    val chatItems: List<ChatItem>
)
