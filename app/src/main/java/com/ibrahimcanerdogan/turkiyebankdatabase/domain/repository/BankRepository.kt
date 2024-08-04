package com.ibrahimcanerdogan.turkiyebankdatabase.domain.repository

import com.ibrahimcanerdogan.turkiyebankdatabase.common.Resource
import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankData
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    fun getBankDataRepository(): Flow<Resource<BankData>>
}