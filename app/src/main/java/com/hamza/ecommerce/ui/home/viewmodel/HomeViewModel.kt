package com.hamza.ecommerce.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.products.ProductModel
import com.hamza.ecommerce.data.models.products.ProductSaleType
import com.hamza.ecommerce.data.models.user.CountryData
import com.hamza.ecommerce.data.repository.category.CategoriesRepository
import com.hamza.ecommerce.data.repository.home.SalesAdsRepository
import com.hamza.ecommerce.data.repository.products.ProductsRepository
import com.hamza.ecommerce.data.repository.user.UserPreferenceRepository
import com.hamza.ecommerce.domains.mappers.toProductUIModel
import com.hamza.ecommerce.ui.products.models.ProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val salesAdsRepository: SalesAdsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val userPreferenceRepository: UserPreferenceRepository,
    private val productsRepository: ProductsRepository,
) :
    ViewModel() {


    val salesAdsState = salesAdsRepository.getSalesAds().stateIn(
        viewModelScope + IO,
        started = SharingStarted.Eagerly,
        initialValue = Resource.Loading(),
    )

    val categoriesState = categoriesRepository.getCategories().stateIn(
        viewModelScope + IO,
        started = SharingStarted.Eagerly,
        initialValue = Resource.Loading(),
    )
    val countryState = userPreferenceRepository.getUserCountry().stateIn(
        viewModelScope + IO,
        started = SharingStarted.Eagerly,
        initialValue = CountryData.getDefaultInstance()
    )
    val flashSaleState = getProductsSales(ProductSaleType.FLASH_SALE)

    val megaSaleState = getProductsSales(ProductSaleType.MEGA_SALE)

    val isEmptyFlashSale = flashSaleState.map { it.isEmpty() }.asLiveData()

    val isEmptyMegaSale = megaSaleState.map { it.isEmpty() }.asLiveData()


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getProductsSales(productSaleType: ProductSaleType): StateFlow<List<ProductUIModel>> =
        countryState.mapLatest {
            Log.d(TAG, "CountryId for flash sale: ${it.id}")
            productsRepository.getSaleProducts(it.id ?: "0", productSaleType.type, 10)
        }.mapLatest { it.first().map { getProductModel(it) } }.stateIn(
            viewModelScope + IO, started = SharingStarted.Eagerly, initialValue = emptyList()
        )

    private fun getProductModel(product: ProductModel): ProductUIModel {
        val productUIModel = product.toProductUIModel().copy(
            currencySymbol = countryState.value?.currencySymbol ?: ""
        )
        return productUIModel
    }

    fun stopTimer() {
        salesAdsState.value.data?.forEach {
            it.stopCountdown()
        }
    }

    fun startTimer() {
        salesAdsState.value.data?.forEach {
            it.startCountdown()
        }
    }


    companion object {
        private const val TAG = "HomeViewModel"
    }
}

