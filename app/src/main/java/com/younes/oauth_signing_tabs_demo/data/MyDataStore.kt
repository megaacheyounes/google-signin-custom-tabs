package com.younes.oauth_signing_tabs_demo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class MyDataStore(private val context: Context) {

    private val GOOGLE_SIGNIN_CODE = stringPreferencesKey("code")
    private val GOOGLE_ACCESS_TOKEN = stringPreferencesKey("accessToken")

    val googleSignInCodeFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[GOOGLE_SIGNIN_CODE]
        }

    suspend fun saveGoogleSignInCode(value: String) {
        context.dataStore.edit { settings ->
            settings[GOOGLE_SIGNIN_CODE] = value
        }
    }

    suspend fun deleteCode() {
        context.dataStore.edit { settings ->
            settings.remove(GOOGLE_SIGNIN_CODE)
        }
    }


    val googleAccessToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[GOOGLE_ACCESS_TOKEN]
        }

    suspend fun saveGoogleAccessToken(value: String) {
        context.dataStore.edit { settings ->
            settings[GOOGLE_ACCESS_TOKEN] = value
        }
    }

    suspend fun clear() {
        context.dataStore.edit { settings ->
            settings.clear()
        }
    }
}