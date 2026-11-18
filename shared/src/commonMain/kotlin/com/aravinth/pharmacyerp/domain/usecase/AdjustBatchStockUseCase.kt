package com.aravinth.pharmacyerp.domain.usecase

import com.aravinth.pharmacyerp.domain.model.inventory.EventType
import com.aravinth.pharmacyerp.domain.model.inventory.StockLog
import com.aravinth.pharmacyerp.domain.repository.inventory.BatchRepository
import com.aravinth.pharmacyerp.domain.repository.inventory.StockLogRepository

class AdjustBatchStockUseCase(
    private val batchRepository: BatchRepository,
    private val stockLogRepository: StockLogRepository
){
    suspend operator fun invoke(
        batchId: Long,
        quantityDelta: Int,
        eventType: EventType,
        operatorName: String,
        voucherRef: String = "",
        timeStampEpochMillis: Long
    ): Result<Unit>
    {
    if(quantityDelta == 0) {
        return Result.failure(IllegalArgumentException("Adjustment quantity cannot be zero"))
    }
     val batch = batchRepository.getBatchById(batchId)
         ?: return Result.failure(IllegalArgumentException("Batch not found"))

     val updatedStock = batch.currentStock + quantityDelta
        if(updatedStock < 0) {
            return Result.failure(IllegalArgumentException("Insufficient stock in batch"))
        }

     val updatedBatch = batch.copy(currentStock = updatedStock)
        batchRepository.upsertBatch(updatedBatch)

        val stockLog = StockLog(
            itemId = batch.itemId,
            batchId = batch.id,
            batchNumber = batch.batchNumber,
            timeStampEpochMillis = timeStampEpochMillis,
            stockEventType = eventType,
            operatorName = operatorName,
            voucherRef = voucherRef,
            quantityShift = quantityDelta.toDouble()
        )
        stockLogRepository.insertStockLog(stockLog)

        return Result.success(Unit)
    }
}