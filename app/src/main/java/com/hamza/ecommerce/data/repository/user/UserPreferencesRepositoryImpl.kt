package com.hamza.ecommerce.data.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.hamza.ecommerce.data.datasource.datastore.DataStoreKeys.IS_USER_LOGGED_IN
import com.hamza.ecommerce.data.datasource.datastore.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(private val userPreferencesDataStore: UserPreferencesDataStore) :
    UserPreferencesRepository {


    override suspend fun saveUserLoggedInState(isUserLoggedIn: Boolean) =
        userPreferencesDataStore.saveUserLoggedInState(isUserLoggedIn)

    override suspend fun isUserLoggedIn(): Flow<Boolean> = userPreferencesDataStore.isUserLoggedIn

    override suspend fun saveUserId(userId: String) = userPreferencesDataStore.saveUserId(userId)
    override fun getUserId(): Flow<String?> = userPreferencesDataStore.userId


}