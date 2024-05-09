package com.alanturing.cpifp.datastore.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WhatsAppCloneAPI {

    @POST("user")
    suspend fun register(@Body phone:UserRequest): Response<UserResponse>
    @GET("user")
    suspend fun readUser(@Query("phone") phone:String): Response<UserResponse>
}