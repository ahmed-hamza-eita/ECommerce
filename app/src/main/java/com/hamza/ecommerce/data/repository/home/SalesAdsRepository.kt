package com.hamza.ecommerce.data.repository.home

import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.ui.home.models.SalesAdUIModel
import kotlinx.coroutines.flow.Flow

interface SalesAdsRepository {
      fun getSalesAds(): Flow<Resource<List<SalesAdUIModel>>>}