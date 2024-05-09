package com.alanturing.cpifp.datastore.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alanturing.cpifp.datastore.data.api.UserRequest
import com.alanturing.cpifp.datastore.data.api.WhatsAppCloneAPI
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton
private val PHONE_KEY  = stringPreferencesKey("phone")
// At the top level of your kotlin file:
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@Singleton
class RegisterRepository @Inject constructor(@ApplicationContext val context:Context,
                                             private val api:WhatsAppCloneAPI) {

    suspend fun register(phone:String):Boolean {
        val response = api.register(UserRequest(phone=phone))

        return if (response.isSuccessful) {
            context.dataStore.edit { settings ->
                settings[PHONE_KEY] = phone
            }
            true
        } else {
            false
        }

    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun isRegistered(): Boolean {
      val localPhone = context.dataStore.data.map {
          it[PHONE_KEY] ?: ""
      }.first()

        // Si el telefono local es vacio, no estoy registrado
        return if (localPhone.isBlank()) {
            false
        }
        else {
            val response = api.readUser(localPhone)
            response.isSuccessful && response.body()!!.phone == localPhone
        }

    }
}