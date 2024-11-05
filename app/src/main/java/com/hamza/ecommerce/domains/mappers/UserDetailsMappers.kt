package com.hamza.ecommerce.domains.mappers

import com.hamza.ecommerce.data.models.user.CountryData
import com.hamza.ecommerce.data.models.user.UserDetailsModel
import com.hamza.ecommerce.data.models.user.UserDetailsPreferences

//Mapper link between UI and DataSource layer


//convert  Preferences to Model for using it in UI
fun UserDetailsPreferences.toUserDetailsModel(): UserDetailsModel {
    return UserDetailsModel(
        id = id,
        email = email,
        name = name,
        reviews = reviewsList
    )
}

//convert Model to Preferences for using it in dataSource layer
fun UserDetailsModel.toUserDetailsPreferences(countryData: CountryData): UserDetailsPreferences {
    return UserDetailsPreferences.newBuilder()
        .setId(id)
        .setEmail(email)
        .setName(name)
        .addAllReviews(reviews?.toList() ?: emptyList())
        .setCountry(countryData)
        .build()
}