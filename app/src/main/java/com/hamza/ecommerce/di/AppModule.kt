package com.hamza.ecommerce.di

import android.app.Application
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hamza.ecommerce.data.datasource.datastore.AppPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAppPreferencesDataSource(appContext: Application) =
        AppPreferencesDataSource(appContext)

}