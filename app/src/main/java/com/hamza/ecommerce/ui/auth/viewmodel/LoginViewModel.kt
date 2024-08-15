package com.hamza.ecommerce.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepository
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepository
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepositoryImpl
import com.hamza.ecommerce.ui.common.viewmodel.UserViewModel
import com.hamza.ecommerce.utils.isValidEmail
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userPrefs: UserPreferencesRepository,
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _loginState = MutableSharedFlow<Resource<String>>()
    val loginState = _loginState.asSharedFlow()


    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val isLoginValid: Flow<Boolean> = combine(email, password) { email, password ->
        email.trim().isValidEmail() && password.trim().length >= 6
    }

    fun loginWithEmailAndPassword() = viewModelScope.launch {
        val email = email.value
        val password = password.value
        if (isLoginValid.first()) {
            authRepository.loginWithEmailAndPassword(email.trim(), password).onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _loginState.emit(Resource.Loading())
                    }


                    is Resource.Success -> {
                        _loginState.emit(Resource.Success(resource.data ?: "Empty user Id"))
                    }

                    is Resource.Error -> {
                        _loginState.emit(
                            Resource.Error(resource.exception ?: Exception("Unknown error"))
                        )
                    }
                }
            }.launchIn(viewModelScope)

        } else {
            _loginState.emit(Resource.Error(Exception("Invalid Email or Password")))
        }

    }

    fun loginWithGoogle(idToken: String) = viewModelScope.launch {
        authRepository.loginWithGoogle(idToken).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _loginState.emit(Resource.Loading())
                }

                is Resource.Success -> {
                    _loginState.emit(Resource.Success(resource.data ?: "Empty user Id"))
                }

                is Resource.Error -> {
                    _loginState.emit(
                        Resource.Error(
                            resource.exception ?: Exception("Unknown error")
                        )
                    )
                }
            }


        }.launchIn(viewModelScope)
    }
}


class LoginViewModelFactory(
    private val userPrefs: UserPreferencesRepository,
    private val authRepository: FirebaseAuthRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return LoginViewModel(userPrefs, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

