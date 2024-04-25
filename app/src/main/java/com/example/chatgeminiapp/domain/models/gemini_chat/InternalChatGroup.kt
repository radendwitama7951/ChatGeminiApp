package com.example.chatgeminiapp.domain.models.gemini_chat

data class InternalChatGroup (
    val id: Long,
    val title: String?,
    val createdAt: String,
    val editedAt: Long
)