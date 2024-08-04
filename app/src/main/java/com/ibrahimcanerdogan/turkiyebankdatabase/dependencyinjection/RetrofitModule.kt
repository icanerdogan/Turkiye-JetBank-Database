package com.ibrahimcanerdogan.turkiyebankdatabase.dependencyinjection

import com.ibrahimcanerdogan.turkiyebankdatabase.common.Constants.BASE_URL
import com.ibrahimcanerdogan.turkiyebankdatabase.data.network.BankAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideBankService(): BankAPIService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BankAPIService::class.java)
}