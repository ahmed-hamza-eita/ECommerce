package com.hamza.ecommerce.data.models.categories

import android.os.Parcelable
import androidx.annotation.Keep
import com.hamza.ecommerce.ui.home.models.CategoryUIModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CategoryModel(
    val icon: String = "",
    val name: String = "",
    val id: String = ""
) : Parcelable {
    fun toUIModel(): CategoryUIModel {
        return CategoryUIModel(
            icon = this.icon,
            name = this.name,
            id = this.id
        )
    }
}