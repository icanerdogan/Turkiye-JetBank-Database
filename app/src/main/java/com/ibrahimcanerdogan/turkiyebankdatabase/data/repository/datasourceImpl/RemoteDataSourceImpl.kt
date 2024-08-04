package com.ibrahimcanerdogan.turkiyebankdatabase.data.repository.datasourceImpl

import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankData
import com.ibrahimcanerdogan.turkiyebankdatabase.data.network.BankAPIService
import com.ibrahimcanerdogan.turkiyebankdatabase.data.repository.datasource.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val bankAPIService: BankAPIService
): RemoteDataSource {

    override suspend fun getBankDataSource(): BankData {
        return bankAPIService.getBankDataNetwork()
    }
}