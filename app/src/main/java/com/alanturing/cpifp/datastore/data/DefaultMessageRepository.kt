package com.alanturing.cpifp.datastore.data

import com.alanturing.cpifp.datastore.data.model.Message

class DefaultMessageRepository constructor(
    private val localRepository: MessageRepository,
    private val networkRepository: MessageRepository
): MessageRepository {
    override suspend fun getMessages(): List<Message> {
        val remoteMessages = networkRepository.getMessages()
        createMessages(remoteMessages)
        return localRepository.getMessages()
    }

    override suspend fun createMessages(messages: List<Message>) {
        TODO("Not yet implemented")
    }
}