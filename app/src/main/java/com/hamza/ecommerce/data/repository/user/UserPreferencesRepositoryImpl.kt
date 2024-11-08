package com.hamza.ecommerce.data.repository.user

import android.content.Context
import android.util.Log
import com.hamza.ecommerce.data.datasource.datastore.userDetailsDataStore
import com.hamza.ecommerce.data.models.user.CountryData
import com.hamza.ecommerce.data.models.user.UserDetailsPreferences
import com.hamza.ecommerce.ui.auth.models.CountryUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(private val context: Context) :
    UserPreferenceRepository {


    override fun getUserDetails(): Flow<UserDetailsPreferences> {
        return context.userDetailsDataStore.data
    }

    override suspend fun updateUserId(userId: String) {
        context.userDetailsDataStore.updateData { preferences ->
            preferences.toBuilder().setId(userId).build()
        }
    }

    override suspend fun getUserId(): Flow<String> {
        return context.userDetailsDataStore.data.map { it.id }
    }

    override suspend fun clearUserPreferences() {
        context.userDetailsDataStore.updateData { preferences ->
            preferences.toBuilder().clear().build()
        }
    }

    override suspend fun updateUserDetails(userDetailsPreferences: UserDetailsPreferences) {
        context.userDetailsDataStore.updateData { userDetailsPreferences }
    }


    override suspend fun saveUserCountry(countryId: CountryUIModel) {
        val countryData = CountryData.newBuilder()
            .setId(countryId.id)
            .setCode(countryId.code)
            .setName(countryId.name)
            .setCurrency(countryId.currency)
            .setCurrencySymbol(countryId.currencySymbol)
            .build()

        if (countryData != null) {
            context.userDetailsDataStore.updateData { preferences ->
                preferences.toBuilder().setCountry(countryData).build()
            }
        } else {
            Log.e("UserPreferenceRepositoryImpl", "CountryData is null")
        }
    }

    override fun getUserCountry(): Flow<CountryData> {
        return context.userDetailsDataStore.data.map { it.country }
    }


}