package com.alanturing.cpifp.datastore.data.api

import com.alanturing.cpifp.datastore.data.MessageRepository
import com.alanturing.cpifp.datastore.data.api.WhatsAppCloneAPI
import com.alanturing.cpifp.datastore.data.model.Message

class MessageNetworkRepository(
    private val api:WhatsAppCloneAPI
):MessageRepository {
    override suspend fun getMessages(): List<Message> {
        val response = api.getAllMesages()

        return if (response.isSuccessful) response.body()!!.toExternalModel()
                else emptyList()

    }

    override suspend fun createMessages(messages: List<Message>) {
        TODO("Not yet implemented")
    }


}