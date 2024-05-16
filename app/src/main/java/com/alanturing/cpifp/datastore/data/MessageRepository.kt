package com.alanturing.cpifp.datastore.data

import com.alanturing.cpifp.datastore.data.model.Message

interface MessageRepository {

    suspend fun getMessages(): List<Message>

    suspend fun createMessages(messages:List<Message>)
}