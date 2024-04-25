package com.example.chatgeminiapp.domain.usecases.gemini_chat

import android.util.Log
import com.example.chatgeminiapp._common.resources.Constants.MODEL_ROLE
import com.example.chatgeminiapp._common.resources.Constants.USER_ROLE
import com.example.chatgeminiapp.domain._common.utils.chatItemToInternal
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class SwitchChatGroup @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(chatGroupId: Long): List<InternalChat> {
        val targetChatGroupChats =
            geminiChatRepository.selectChatGroup(chatGroupId).chatItems

        val chatItemsFiltered = geminiChatRepository.filterChatItems(targetChatGroupChats)
        geminiChatRepository.initChat(chatItemsFiltered.reversed())

        val allChatOfSelectedGroup = chatItemsFiltered.map {
            geminiChatRepository.chatItemToInternal(it)
        }

        return allChatOfSelectedGroup
    }
}