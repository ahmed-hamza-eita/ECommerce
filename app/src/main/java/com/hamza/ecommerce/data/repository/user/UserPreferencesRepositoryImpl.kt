package com.hamza.ecommerce.data.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.hamza.ecommerce.data.datasource.datastore.DataStoreKeys.IS_USER_LOGGED_IN
import com.hamza.ecommerce.data.datasource.datastore.dataStore
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(private val context: Context) : UserPreferencesRepository {


    override suspend fun isUserLoggedIn() = context.dataStore.data.map { preferences ->
        preferences[IS_USER_LOGGED_IN] ?: false
    }


    override suspend fun saveUserLoggedIn(isUserLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }
}