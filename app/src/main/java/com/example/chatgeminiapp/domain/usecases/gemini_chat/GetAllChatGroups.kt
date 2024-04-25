package com.example.chatgeminiapp.domain.usecases.gemini_chat

import android.util.Log
import com.example.chatgeminiapp.domain._common.utils.chatGroupToInternal
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.example.chatgeminiapp.domain.repositories.GeminiChatRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllChatGroup @Inject constructor(
    private val geminiChatRepository: GeminiChatRepository
) {
    suspend operator fun invoke(): List<InternalChatGroup> {
        val chatGroups = geminiChatRepository.getChatGroups()
        val chatList = chatGroups.map { group -> geminiChatRepository.chatGroupToInternal(group) }

        return chatList
    }

}
