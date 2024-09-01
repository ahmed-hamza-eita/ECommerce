package com.hamza.ecommerce.ui.home.viewmodel

import androidx.lifecycle.ViewModel



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