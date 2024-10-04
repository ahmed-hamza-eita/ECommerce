package com.hamza.ecommerce.data.repository.home

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.sales_ads.SalesAdModel
import com.hamza.ecommerce.ui.auth.viewmodel.LoginViewModel.Companion.TAG
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SalesAdsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : SalesAdsRepository {
    override fun getSalesAds() = flow {
        try {
            Log.d(TAG, "getSalesAds: ")
            emit(Resource.Loading())
            val salesAds =
                firestore.collection("sales_ads")
                    .get().await().toObjects(SalesAdModel::class.java)

            emit(Resource.Success(salesAds.map { it.toUIModel() }))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }


}