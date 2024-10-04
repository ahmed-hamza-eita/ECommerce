package com.hamza.ecommerce.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.sales_ads.SalesAdModel
import com.hamza.ecommerce.data.repository.home.SalesAdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val salesAdRepository: SalesAdRepository) :
    ViewModel() {




    val salesAdsState = salesAdRepository.getSalesAds().stateIn(
        viewModelScope + IO,
        started = SharingStarted.Eagerly,
        initialValue = Resource.Loading(),
    )



}

