package com.hamza.ecommerce.ui.auth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.user.UserDetailsModel
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepository
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl
import com.hamza.ecommerce.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(

    private val authRepository: FirebaseAuthRepository
) : ViewModel() {
    private val _restPasswordState = MutableSharedFlow<Resource<String>>()
    val restPasswordState = _restPasswordState.asSharedFlow()

    val email = MutableStateFlow("")


    fun resetPassword() = viewModelScope.launch(IO) {

        if (email.value.isValidEmail()) {
            _restPasswordState.emit(Resource.Loading())
            authRepository.resetPassword(email.value).collect {
                _restPasswordState.emit(it)
            }
        } else {
            _restPasswordState.emit(Resource.Error(Exception("Invalid email")))
        }
    }

}

