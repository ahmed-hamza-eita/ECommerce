package com.hamza.ecommerce.ui.auth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepository
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl

import kotlinx.coroutines.flow.MutableStateFlow

class ForgetPasswordViewModel(

    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    val email = MutableStateFlow("")
    fun resetPassword() {
        val email = email.value

    }
}

class ForgetPasswordViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    private val firebaseAuthRepository = FirebaseAuthRepositoryImpl()
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ForgetPasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ForgetPasswordViewModel(

                firebaseAuthRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}