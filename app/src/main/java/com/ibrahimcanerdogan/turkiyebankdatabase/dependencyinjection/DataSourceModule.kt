package com.ibrahimcanerdogan.turkiyebankdatabase.dependencyinjection

import com.ibrahimcanerdogan.turkiyebankdatabase.data.network.BankAPIService
import com.ibrahimcanerdogan.turkiyebankdatabase.data.repository.datasource.RemoteDataSource
import com.ibrahimcanerdogan.turkiyebankdatabase.data.repository.datasourceImpl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(bankAPIService: BankAPIService): RemoteDataSource {
        return RemoteDataSourceImpl(bankAPIService)
    }
}