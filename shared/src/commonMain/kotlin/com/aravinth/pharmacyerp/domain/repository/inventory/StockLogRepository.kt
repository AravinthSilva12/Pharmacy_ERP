package com.aravinth.pharmacyerp.domain.repository.inventory

import com.aravinth.pharmacyerp.domain.model.inventory.StockLog
import kotlinx.coroutines.flow.Flow

interface StockLogRepository {
    suspend fun insertStockLog(log: StockLog): Long

    fun getLogsByItemId(itemId: Long): Flow<List<StockLog>>

    fun getAllStockLogs(): Flow<List<StockLog>>
}