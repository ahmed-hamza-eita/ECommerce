package com.hamza.ecommerce.data.datasource.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.hamza.ecommerce.data.datasource.datastore.DataStoreKeys.IS_USER_LOGGED_IN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    suspend fun saveUserLoggedInState(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.USER_ID] = userId
        }
    }

    val isUserLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_USER_LOGGED_IN] ?: false
    }

    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[DataStoreKeys.USER_ID]
    }

}