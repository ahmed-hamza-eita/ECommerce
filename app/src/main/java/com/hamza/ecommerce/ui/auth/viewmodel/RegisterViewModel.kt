package com.hamza.ecommerce.ui.auth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.hamza.ecommerce.data.datasource.datastore.AppPreferencesDataSource
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.user.UserDetailsModel
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepository
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl
import com.hamza.ecommerce.data.repository.common.AppDataStoreRepositoryImpl
import com.hamza.ecommerce.data.repository.common.AppPreferenceRepository
import com.hamza.ecommerce.data.repository.user.UserPreferenceRepository
import com.hamza.ecommerce.data.repository.user.UserPreferenceRepositoryImpl
import com.hamza.ecommerce.utils.isValidEmail
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val appPreferencesRepository: AppPreferenceRepository,
    private val userPreferencesRepository: UserPreferenceRepository,
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {
    private val _registerState = MutableSharedFlow<Resource<UserDetailsModel>>()
    val registerState = _registerState.asSharedFlow()

    val name = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

    private val isRegisterValid =
        combine(name, email, password, confirmPassword) { name, email, password, confirmPassword ->
            name.trim().isNotEmpty()
                    && email.trim().isValidEmail()
                    && password.trim().length >= 6
                    && confirmPassword == password
        }

    fun registerWithEmailAndPassword() = viewModelScope.launch(IO) {
        val name = name.value
        val email = email.value
        val password = password.value
        val confirmPassword = confirmPassword.value
        if (isRegisterValid.first()) {

            authRepository.registerWithEmailAndPassword(
                email = email,
                name = name,
                password = password
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _registerState.emit(Resource.Loading())
                    is Resource.Success -> {
                        authRepository.sendEmailVerification()
                        _registerState.emit(Resource.Success(resource.data!!))
                    }

                    is Resource.Error -> _registerState.emit(Resource.Error(Exception(resource.exception?.message)))
                }
            }

        } else {
            _registerState.emit(Resource.Error(Exception("Invalid registration details")))
        }
    }
}

class RegisterViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    private val appPreferencesRepository =
        AppDataStoreRepositoryImpl(AppPreferencesDataSource(context))
    private val userPreferencesRepository = UserPreferenceRepositoryImpl(context)
    private val firebaseAuthRepository = FirebaseAuthRepositoryImpl()
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return RegisterViewModel(
                appPreferencesRepository,
                userPreferencesRepository,
                firebaseAuthRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}