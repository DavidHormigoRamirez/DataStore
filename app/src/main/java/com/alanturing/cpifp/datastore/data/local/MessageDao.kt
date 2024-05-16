package com.alanturing.cpifp.datastore.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface  MessageDao {
    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): List<MessageEntity>

    @Insert
    suspend fun createMessage(message:MessageEntity)
}