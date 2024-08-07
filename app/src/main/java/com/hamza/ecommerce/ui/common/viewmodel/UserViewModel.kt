package com.hamza.ecommerce.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepositoryImpl

class UserViewModel(private val userPreferencesRepository: UserPreferencesRepositoryImpl) :
    ViewModel() {

    suspend fun isUserLoggedIn() = userPreferencesRepository.isUserLoggedIn()
}

class UserViewModelFactory(private val userPreferencesRepository: UserPreferencesRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return UserViewModel(userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}