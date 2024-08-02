package com.hamza.ecommerce.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.hamza.ecommerce.data.datastore.DataStoreKeys.E_COMMERCE_PREFERENCES


object DataStoreKeys {
    const val E_COMMERCE_PREFERENCES = "e_commerce_preferences"
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(E_COMMERCE_PREFERENCES)
