package com.alanturing.cpifp.datastore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alanturing.cpifp.datastore.data.model.Message

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Long,
    val text: String,
)

fun List<MessageEntity>.toExternalModel(): List<Message> {
    return this.map {
        Message(
            id = it.id,
            text = it.text
        )
    }
}
