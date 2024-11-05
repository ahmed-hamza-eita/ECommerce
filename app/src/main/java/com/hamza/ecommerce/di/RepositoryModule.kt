package com.hamza.ecommerce.di

import com.hamza.ecommerce.data.repository.auth.CountryRepository
import com.hamza.ecommerce.data.repository.auth.CountryRepositoryImpl
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepository
import com.hamza.ecommerce.data.repository.auth.FirebaseAuthRepositoryImpl
import com.hamza.ecommerce.data.repository.category.CategoriesRepository
import com.hamza.ecommerce.data.repository.category.CategoriesRepositoryImpl
import com.hamza.ecommerce.data.repository.common.AppDataStoreRepositoryImpl
import com.hamza.ecommerce.data.repository.common.AppPreferenceRepository
import com.hamza.ecommerce.data.repository.home.SalesAdsRepository
import com.hamza.ecommerce.data.repository.home.SalesAdsRepositoryImpl
import com.hamza.ecommerce.data.repository.products.ProductsRepository
import com.hamza.ecommerce.data.repository.products.ProductsRepositoryImpl
import com.hamza.ecommerce.data.repository.user.UserFirestoreRepository
import com.hamza.ecommerce.data.repository.user.UserFirestoreRepositoryImpl
import com.hamza.ecommerce.data.repository.user.UserPreferenceRepository
import com.hamza.ecommerce.data.repository.user.UserPreferenceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl)
            : FirebaseAuthRepository

    @Binds
    @Singleton
    abstract fun bindAppPreferenceRepository(appPreferenceRepositoryImpl: AppDataStoreRepositoryImpl)
            : AppPreferenceRepository


    @Binds
    @Singleton
    abstract fun bindUserPreferenceRepository(userPreferenceRepositoryImpl: UserPreferenceRepositoryImpl)
            : UserPreferenceRepository


    @Binds
    @Singleton
    abstract fun bindUserFirestoreRepository(userFirestoreRepositoryImpl: UserFirestoreRepositoryImpl)
            : UserFirestoreRepository


    @Binds
    @Singleton
    abstract fun bindSalesAdRepository(salesAdRepositoryImpl: SalesAdsRepositoryImpl)
            : SalesAdsRepository

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(categoriesRepositoryImpl: CategoriesRepositoryImpl)
            : CategoriesRepository

    @Binds
    @Singleton
    abstract fun provideProductsRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository


    @Binds
    @Singleton
    abstract fun provideCountryRepository(
        countryRepositoryImpl: CountryRepositoryImpl
    ): CountryRepository
}