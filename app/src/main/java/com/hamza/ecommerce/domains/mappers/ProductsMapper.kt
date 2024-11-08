package com.hamza.ecommerce.domains.mappers

import com.hamza.ecommerce.data.models.products.ProductColorModel
import com.hamza.ecommerce.data.models.products.ProductModel
import com.hamza.ecommerce.data.models.products.ProductSizeModel
import com.hamza.ecommerce.ui.products.models.ProductColorUIModel
import com.hamza.ecommerce.ui.products.models.ProductUIModel


fun ProductUIModel.toProductModel(): ProductModel {
    return ProductModel(
        id = id,
        name = name,
        description = description,
        categoriesIDs = categoriesIDs,
        images = images,
        price = price,
        rate = rate,
        salePercentage = salePercentage,
        saleType = saleType,
        colors = colors.map {
            ProductColorModel(
                size = it.size,
                stock = it.stock,
                color = it.color
            )
        },
        sizes = sizes.map {
            ProductSizeModel(
                size = it.size,
                stock = it.stock
            )
        }
    )
}

fun ProductModel.toProductUIModel(): ProductUIModel {
    return ProductUIModel(
        id = id ?: throw IllegalArgumentException("Product ID is missing"),
        name = name ?: "No Name",
        description = description ?: "No Description",
        categoriesIDs = categoriesIDs ?: emptyList(),
        images = images ?: emptyList(),
        price = price ?: 0,
        rate = rate ?: 0f,
        salePercentage = salePercentage,
        saleType = saleType,
        colors = colors?.map {
            ProductColorUIModel(
                size = it.size,
                stock = it.stock,
                color = it.color
            )
        } ?: emptyList(),
        sizes = sizes?.map {
            ProductSizeModel(
                size = it.size,
                stock = it.stock
            )
        } ?: emptyList()
    )
}
