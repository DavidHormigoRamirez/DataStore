package com.alanturing.cpifp.datastore.data.api

data class MessageRequest(
    val text: String,
    val timeSent: String,
    val senderPhone: String,
    val receiverPhone: String
)
