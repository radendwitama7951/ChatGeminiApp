package com.example.chatgeminiapp.domain.models.gemini_chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatItem(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    val role: String? = "model",
    val prompt: String? = null,
    val bitmapSource: String? = null,

    val chatGroupOwner: Long // For Foreign Key
) {

}
