package com.aravinth.pharmacyerp.data.repository.inventory

import com.aravinth.pharmacyerp.domain.model.inventory.StockLog
import com.aravinth.pharmacyerp.domain.repository.inventory.StockLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryStockLogRepository(initialLogs: List<StockLog> = emptyList()) : StockLogRepository {

    private val _stockLogs = MutableStateFlow(initialLogs)

    override suspend fun insertStockLog(log: StockLog): Long {
        var savedId = log.id
        _stockLogs.update { logs ->
            if (savedId == 0L) {
                savedId = (logs.maxOfOrNull { it.id } ?: 0L) + 1L
            }
            logs + log.copy(id = savedId)
        }
        return savedId
    }

    override fun getLogsByItemId(itemId: Long): Flow<List<StockLog>> {
            return _stockLogs.map {log ->
                log.filter {it.itemId == itemId}
            }
    }

    override fun getAllStockLogs(): Flow<List<StockLog>> {
         return _stockLogs.map { logs ->
             logs.sortedByDescending {it.timeStampEpochMillis}
         }
    }
}