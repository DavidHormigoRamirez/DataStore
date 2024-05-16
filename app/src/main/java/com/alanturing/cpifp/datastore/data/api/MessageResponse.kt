package com.alanturing.cpifp.datastore.data.api

import com.alanturing.cpifp.datastore.data.model.Message

data class MessageResponse(
    val id: Long,
    val text: String,
    val timeSent: String,
    val sender: ContactResponse,
    val receiver: ContactResponse
)

fun List<MessageResponse>.toExternalModel(): List<Message> {
    return this.map {
        Message(
            id = it.id,
            text = it.text,
        )
    }
}


