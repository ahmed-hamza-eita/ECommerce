package com.hamza.ecommerce.data.repository.category

import com.hamza.ecommerce.data.models.Resource
import com.hamza.ecommerce.ui.home.models.CategoryUIModel
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getCategories(): Flow<Resource<List<CategoryUIModel>>>

}