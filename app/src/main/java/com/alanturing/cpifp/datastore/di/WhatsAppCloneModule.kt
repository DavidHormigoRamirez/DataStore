package com.alanturing.cpifp.datastore.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alanturing.cpifp.datastore.data.DefaultMessageRepository
import com.alanturing.cpifp.datastore.data.MessageRepository
import com.alanturing.cpifp.datastore.data.api.MessageNetworkRepository
import com.alanturing.cpifp.datastore.data.api.WhatsAppCloneAPI
import com.alanturing.cpifp.datastore.data.local.AppDatabase
import com.alanturing.cpifp.datastore.data.local.MessageDao
import com.alanturing.cpifp.datastore.data.local.MessageLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WhatsAppCloneModule {

    // LOCAL
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context:Context ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            name="app_db"
        ).build()

    }

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase): MessageDao {
        return database.messageDao()
    }

    // NETWORK
    @Provides
    fun networkApi(): WhatsAppCloneAPI {
        val retrofit:Retrofit = Retrofit.Builder().
                baseUrl("https://sterling-impala-mistakenly.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WhatsAppCloneAPI::class.java)
    }

    // REPOSITORIES
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class NetworkRepository
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalRepository

    @Provides
    @Singleton
    @LocalRepository
    fun provideLocalRepository(dao:MessageDao): MessageRepository {
        return MessageLocalRepository(dao)
    }

    @Provides
    @Singleton
    @NetworkRepository
    fun provideNetworkRepository(api:WhatsAppCloneAPI): MessageRepository
    {
        return MessageNetworkRepository(api)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(
        @LocalRepository localRepository: MessageRepository,
        @NetworkRepository networkRepository: MessageRepository
    ): MessageRepository {
        return DefaultMessageRepository(
            localRepository,networkRepository
        )
    }
}