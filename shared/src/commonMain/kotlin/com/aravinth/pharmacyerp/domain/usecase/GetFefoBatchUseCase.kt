package com.aravinth.pharmacyerp.domain.usecase
import com.aravinth.pharmacyerp.domain.model.inventory.Batch
import com.aravinth.pharmacyerp.domain.repository.inventory.BatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFefoBatchUseCase(private val batchRepository: BatchRepository) {
    operator fun invoke(itemId: Long, todayEpochDays: Long): Flow<Batch?> {
        return batchRepository.getBatchesByItemId(itemId).map { batches ->
            batches
                .filter { it.currentStock > 0 && it.isQuarantined == false && it.expiryDateEpochDays > todayEpochDays}
                .minByOrNull { it.expiryDateEpochDays }
        }
    }
}