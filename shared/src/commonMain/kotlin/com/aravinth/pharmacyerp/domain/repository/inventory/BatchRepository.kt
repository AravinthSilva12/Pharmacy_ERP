package com.aravinth.pharmacyerp.domain.repository.inventory
import com.aravinth.pharmacyerp.domain.model.inventory.Batch
import kotlinx.coroutines.flow.Flow

interface BatchRepository {

    fun getAllBatches(): Flow<List<Batch>>

    fun getBatchesByItemId(itemId: Long): Flow<List<Batch>>

    suspend fun getBatchById(id: Long): Batch?

    suspend fun upsertBatch(batch: Batch): Long

    suspend fun updateQuantity(id: Long, currentStock: Int): Unit

    suspend fun recallBatch(id: Long, isQuarantined: Boolean): Unit

}