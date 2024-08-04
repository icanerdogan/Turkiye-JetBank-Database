package com.ibrahimcanerdogan.turkiyebankdatabase.data.repository.datasource

import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankData

interface RemoteDataSource {
    suspend fun getBankDataSource(): BankData
}