package com.aravinth.pharmacyerp.data.repository.inventory

import com.aravinth.pharmacyerp.domain.model.inventory.Batch
import com.aravinth.pharmacyerp.domain.repository.inventory.BatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryBatchRepository(initialBatches: List<Batch> = emptyList()) : BatchRepository {

    private val _batches = MutableStateFlow(initialBatches)

    override fun getAllBatches(): Flow<List<Batch>> {
        return _batches.asStateFlow()
    }

    override fun getBatchesByItemId(itemId: Long): Flow<List<Batch>> {
        return _batches.map {batches ->
            batches.filter { it.itemId == itemId}
        }
    }

    override suspend fun getBatchById(id: Long): Batch? {
        return _batches.value.find { it.id == id }
    }

    override suspend fun upsertBatch(batch: Batch): Long {
          var savedId = batch.id
        _batches.update {currentList ->
            if(savedId == 0L){
                savedId = (currentList.maxOfOrNull { it.id } ?: 0L) + 1L
                currentList + batch.copy(id = savedId)
            } else {
                currentList.map { existing ->
                    if(existing.id == savedId) batch else existing
                }
            }
        }
        return savedId
    }

    override suspend fun updateQuantity(id: Long, currentStock: Int): Unit {
             _batches.update {currentList ->
                 currentList.map { existing ->
                     if(existing.id == id) existing.copy(currentStock = currentStock) else existing
                 }
             }
    }

    override suspend fun recallBatch(id: Long, isQuarantined: Boolean) {
            _batches.update {currentList ->
                currentList.map {existing ->
                    if(existing.id == id) existing.copy(isQuarantined = isQuarantined) else existing
                }

            }
    }
}