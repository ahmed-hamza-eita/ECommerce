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
import com.hamza.ecommerce.domains.mappers.toUserDetailsPreferences
import com.hamza.ecommerce.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferenceRepository,
    private val userPreferencesRepository: UserPreferenceRepository,
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _loginState = MutableSharedFlow<Resource<UserDetailsModel>>()
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
            handleFlow { authRepository.loginWithEmailAndPassword(email, password) }
        } else {
            _loginState.emit(Resource.Error(Exception("Invalid email or password")))
        }
    }

    fun loginWithGoogle(idToken: String) = handleFlow { authRepository.loginWithGoogle(idToken) }

    fun loginWithFacebook(idToken: String) =
        handleFlow { authRepository.loginWithFacebook(idToken) }

    private fun handleFlow(performFlow: suspend () -> Flow<Resource<UserDetailsModel>>) =
        viewModelScope.launch(IO) {
            performFlow().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _loginState.emit(Resource.Loading())
                    }

                    is Resource.Success -> {
                        savePreferenceData(resource.data!!)
                        _loginState.emit(Resource.Success(resource.data))
                    }

                    else -> {
                        _loginState.emit(Resource.Error(Exception(resource.exception?.message)))
                    }
                }
            }

        }


    private suspend fun savePreferenceData(userDetailsModel: UserDetailsModel) {
        appPreferencesRepository.saveLoginState(true)
        userPreferencesRepository.updateUserDetails(userDetailsModel.toUserDetailsPreferences())
    }

    fun signOut() {
        authRepository.signOut()
    }

    companion object {
        const val TAG = "LoginViewModel"
    }


}

