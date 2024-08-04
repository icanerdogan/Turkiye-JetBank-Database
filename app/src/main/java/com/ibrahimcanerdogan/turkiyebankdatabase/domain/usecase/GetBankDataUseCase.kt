package com.ibrahimcanerdogan.turkiyebankdatabase.domain.usecase

import com.ibrahimcanerdogan.turkiyebankdatabase.domain.repository.BankRepository
import javax.inject.Inject

class GetBankDataUseCase @Inject constructor(
    private val bankRepository: BankRepository
) {
    fun invoke() = bankRepository.getBankDataRepository()
}