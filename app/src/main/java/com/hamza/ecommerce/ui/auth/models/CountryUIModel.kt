package com.hamza.ecommerce.ui.auth.models

import com.hamza.ecommerce.data.models.user.CountryData

data class CountryUIModel(
    val id: String? = null,
    val name: String? = null,
    val code: String? = null,
    val image: String? = null,
    val currency: String? = null,
    val currencySymbol: String? = null
)

// CountryUIModel.kt
fun CountryUIModel.toDataModel(): CountryData? {
    return CountryData.newBuilder()
        .setId(this.id)
        .setCode(this.code)
        .setName(this.name)
        .setCurrency(this.currency)
        .setCurrencySymbol(this.currencySymbol)
        .build()
}