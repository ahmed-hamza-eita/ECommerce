package com.hamza.ecommerce.data.repository.user

import com.hamza.ecommerce.data.models.user.UserDetailsPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    fun getUserDetails(): Flow<UserDetailsPreferences>
    suspend fun updateUserId(userId: String)
    suspend fun getUserId(): Flow<String>
    suspend fun clearUserPreferences()
    suspend fun updateUserDetails(userDetailsPreferences: UserDetailsPreferences)
}