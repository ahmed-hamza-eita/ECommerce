package com.hamza.ecommerce.data.repository.home

import com.google.firebase.firestore.FirebaseFirestore
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.sales_ads.SalesAdModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SalesAdRepositoryImpl @Inject constructor(private val firebase: FirebaseFirestore) :
    SalesAdRepository {
    override   fun getSalesAds() = flow {
        emit(Resource.Loading())
        try {
            val salesAds = firebase.collection("sales_ads").get().await()
            val salesAdsList = salesAds.toObjects(SalesAdModel::class.java)
            emit(Resource.Success(salesAdsList))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }


}