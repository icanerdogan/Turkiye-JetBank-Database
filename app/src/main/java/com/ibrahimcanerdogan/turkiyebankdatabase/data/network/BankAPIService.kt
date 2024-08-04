package com.ibrahimcanerdogan.turkiyebankdatabase.data.network

import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankData
import retrofit2.http.GET

interface BankAPIService {

    @GET("bankdata")
    suspend fun getBankDataNetwork(): BankData
}