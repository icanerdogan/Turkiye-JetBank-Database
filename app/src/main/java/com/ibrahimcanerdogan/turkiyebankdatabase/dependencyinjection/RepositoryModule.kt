package com.ibrahimcanerdogan.turkiyebankdatabase.dependencyinjection

import com.ibrahimcanerdogan.turkiyebankdatabase.data.repository.BankRepositoryImpl
import com.ibrahimcanerdogan.turkiyebankdatabase.data.repository.datasource.RemoteDataSource
import com.ibrahimcanerdogan.turkiyebankdatabase.domain.repository.BankRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBankRepository(
        remoteDataSource: RemoteDataSource,
    ): BankRepository = BankRepositoryImpl(remoteDataSource)

}