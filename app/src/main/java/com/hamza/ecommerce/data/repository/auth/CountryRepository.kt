package com.hamza.ecommerce.data.repository.auth

 import com.hamza.ecommerce.data.models.auth.CountryModel
 import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getCountries(): Flow<List<CountryModel>>
}