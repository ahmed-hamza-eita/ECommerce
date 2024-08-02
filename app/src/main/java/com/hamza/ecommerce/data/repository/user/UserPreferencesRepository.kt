package com.hamza.ecommerce.data.repository.user

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun isUserLoggedIn(): Flow<Boolean>
    suspend fun saveUserLoggedIn(isUserLoggedIn: Boolean)
}