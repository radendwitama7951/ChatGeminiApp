package com.example.chatgeminiapp.domain._common.utils

import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun chatGroupToInternal (chatGroup: ChatGroup): InternalChatGroup {
    return InternalChatGroup(
        id = chatGroup.id,
        title = chatGroup.title,
        createdAt = SimpleDateFormat(
            "HH:mm MM-dd-yyyy",
            Locale.getDefault()
        ).format(Date(chatGroup.createdAt)),
        editedAt = chatGroup.editedAt
    )
}
fun chatGroupToInternal (chatGroup: ChatGroupWithChatItems): InternalChatGroup {
    return InternalChatGroup(
        id = chatGroup.prop.id,
        title = chatGroup.prop.title,
        createdAt = SimpleDateFormat(
            "HH:mm MM-dd-yyyy",
            Locale.getDefault()
        ).format(Date(chatGroup.prop.createdAt)),
        editedAt = chatGroup.prop.editedAt
    )
}
