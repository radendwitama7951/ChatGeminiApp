package com.example.chatgeminiapp.domain.models.gemini_chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ChatGroup (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "edited_at")
    val editedAt: Long = System.currentTimeMillis(),
    val title: String? = null,
)
