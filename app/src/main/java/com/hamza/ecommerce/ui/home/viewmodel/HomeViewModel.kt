package com.hamza.ecommerce.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hamza.ecommerce.data.repository.user.UserPreferencesRepositoryImpl
import com.hamza.ecommerce.ui.common.viewmodel.UserViewModel

class HomeViewModel : ViewModel() {

}

//class HomeViewModelFactory(
//    private val userPreferencesRepository: UserPreferencesRepositoryImpl
//) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST") return HomeViewModel(
//                userPreferencesRepository
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}