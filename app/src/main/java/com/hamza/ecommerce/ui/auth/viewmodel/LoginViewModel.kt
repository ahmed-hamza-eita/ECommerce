package com.hamza.ecommerce.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepository

class LoginViewModel(private val userPrefs: UserPreferencesRepository) : ViewModel() {


    fun isUserLoggedIn() {
        //userPrefs.isUserLoggedIn()
    }
}