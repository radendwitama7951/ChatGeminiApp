package com.example.chatgeminiapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(chatGroup: ChatItem): Long

    @Delete
    suspend fun delete(chatGroup: ChatItem)

    @Query("SELECT * FROM ChatItem")
    fun getAll(): Flow<List<ChatItem>>

    @Query("SELECT * FROM ChatItem WHERE id=:id")
    suspend fun getById(id: Long): ChatItem
}
