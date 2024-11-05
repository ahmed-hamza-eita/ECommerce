package com.hamza.ecommerce.domains.mappers

import com.hamza.ecommerce.data.models.auth.CountryModel
import com.hamza.ecommerce.ui.auth.models.CountryUIModel


fun CountryModel.toUIModel(): CountryUIModel? {
    return CountryUIModel(
        id = id,
        name = name,
        code = code,
        currency = currency,
        image = image,
        currencySymbol = currencySymbol
    )
}