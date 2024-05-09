package com.alanturing.cpifp.datastore.di

import com.alanturing.cpifp.datastore.data.api.WhatsAppCloneAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class WhatsAppCloneModule {

    @Provides
    fun networkApi(): WhatsAppCloneAPI {
        val retrofit:Retrofit = Retrofit.Builder().
                baseUrl("https://sterling-impala-mistakenly.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WhatsAppCloneAPI::class.java)
    }
}