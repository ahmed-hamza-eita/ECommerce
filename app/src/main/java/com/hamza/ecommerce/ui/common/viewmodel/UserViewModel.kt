package com.hamza.ecommerce.ui.common.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hamza.ecommerce.data.datasource.datastore.AppPreferencesDataSource
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepository
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl
import com.hamza.ecommerce.data.repository.common.AppDataStoreRepositoryImpl
import com.hamza.ecommerce.data.repository.common.AppPreferenceRepository
import com.hamza.ecommerce.data.repository.user.UserPreferenceRepository
import com.hamza.ecommerce.data.repository.user.UserPreferenceRepositoryImpl

class UserViewModel(
    private val appPreferencesRepository: AppPreferenceRepository,
    private val userPreferencesRepository: UserPreferenceRepository,

    private val firebaseAuthRepository: FirebaseAuthRepository
) :
    ViewModel() {
    suspend fun isUserLoggedIn() = appPreferencesRepository.isLoggedIn()

}
class UserViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    private val appPreferencesRepository =
        AppDataStoreRepositoryImpl(AppPreferencesDataSource(context))
    private val userPreferencesRepository = UserPreferenceRepositoryImpl(context)
    private val firebaseAuthRepository = FirebaseAuthRepositoryImpl()
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return UserViewModel(
                appPreferencesRepository,
                userPreferencesRepository,
                firebaseAuthRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
