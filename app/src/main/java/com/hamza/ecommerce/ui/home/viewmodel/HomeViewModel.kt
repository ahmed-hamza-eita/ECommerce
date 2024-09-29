package com.hamza.ecommerce.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {}



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