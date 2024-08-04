package com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.home

import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankDataItem

data class HomeScreenState(
    val isLoading: Boolean = false,
    val bankData: ArrayList<BankDataItem>? = null,
    val errorMessage: String? = null
)