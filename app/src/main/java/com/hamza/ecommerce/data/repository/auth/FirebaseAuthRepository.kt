package com.hamza.ecommerce.data.repository.auth

import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.user.UserDetailsModel
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {


    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<UserDetailsModel>>

    suspend fun loginWithGoogle(idToken: String): Flow<Resource<UserDetailsModel>>
    suspend fun loginWithFacebook(idToken: String): Flow<Resource<UserDetailsModel>>
    suspend fun registerWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<UserDetailsModel>>

    suspend fun sendEmailVerification()

    fun signOut()
}