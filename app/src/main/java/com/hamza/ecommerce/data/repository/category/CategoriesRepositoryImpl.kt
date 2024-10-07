package com.hamza.ecommerce.data.repository.category

import com.google.firebase.firestore.FirebaseFirestore
import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.data.models.categories.CategoryModel
import com.hamza.ecommerce.ui.home.models.CategoryUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class CategoriesRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    CategoriesRepository {
    override fun getCategories(): Flow<Resource<List<CategoryUIModel>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val categories = firestore.collection("categories").get().await()
                    .toObjects(CategoryModel::class.java)
                emit(Resource.Success(categories.map { it.toUIModel() }))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }

}