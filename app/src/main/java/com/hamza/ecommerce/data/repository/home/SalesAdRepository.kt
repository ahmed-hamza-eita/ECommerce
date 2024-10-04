package com.hamza.ecommerce.data.repository.home

import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.sales_ads.SalesAdModel
import kotlinx.coroutines.flow.Flow

interface SalesAdRepository {
      fun getSalesAds():  Flow<Resource<List<SalesAdModel>>>
}