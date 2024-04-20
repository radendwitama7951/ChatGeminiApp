package com.example.chatgeminiapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroupWithChatItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(chatGroup: ChatGroup): Long

    @Delete
    suspend fun delete(chatGroup: ChatGroup)

    @Query("SELECT * FROM ChatGroup")
    fun getAll(): Flow<List<ChatGroup>>

    @Query("SELECT * FROM ChatGroup WHERE id=:id")
    suspend fun getById(id: Long): ChatGroup

    @Transaction
    @Query("SELECT * FROM ChatGroup")
    fun getFlatAll(): Flow<List<ChatGroupWithChatItems>>

    @Transaction
    @Query("SELECT * FROM ChatGroup WHERE id=:id")
    suspend fun getFlatById(id: Long): ChatGroupWithChatItems
}