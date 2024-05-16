package com.alanturing.cpifp.datastore.data.local

import com.alanturing.cpifp.datastore.data.MessageRepository
import com.alanturing.cpifp.datastore.data.model.Message

class MessageLocalRepository(
    private val messageDao: MessageDao
):MessageRepository {
    override suspend fun getMessages(): List<Message> {
        val messages = messageDao.getAllMessages()
        return messages.toExternalModel()
    }

    override suspend fun createMessages(messages: List<Message>) {
        TODO("Not yet implemented")
    }


}