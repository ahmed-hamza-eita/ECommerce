package com.hamza.ecommerce.data.repository.home

import com.google.firebase.firestore.FirebaseFirestore
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.sales_ads.SalesAdModel
import com.hamza.ecommerce.ui.home.models.SalesAdUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SalesAdRepositoryImpl @Inject constructor(private val firebase: FirebaseFirestore) :
    SalesAdRepository {
    override fun getSalesAds(): Flow<Resource<List<SalesAdUIModel>>> = flow {
        emit(Resource.Loading())
        try {
            val salesAds =
                firebase.collection("sales_ads").get().await().toObjects(SalesAdModel::class.java)
            emit(Resource.Success(salesAds.map { it.toUIModel() }))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }


}