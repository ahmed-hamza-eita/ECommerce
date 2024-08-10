package com.hamza.ecommerce.data.repository.user

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun saveUserLoggedInState(isUserLoggedIn: Boolean)
    suspend fun isUserLoggedIn(): Flow<Boolean>
    suspend fun saveUserId(userId: String)
    fun getUserId(): Flow<String?>


}