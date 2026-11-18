package com.aravinth.pharmacyerp.domain.usecase

import com.aravinth.pharmacyerp.domain.model.inventory.Batch
import com.aravinth.pharmacyerp.domain.model.inventory.Item
import com.aravinth.pharmacyerp.domain.repository.inventory.BatchRepository
import com.aravinth.pharmacyerp.domain.repository.inventory.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


data class InventoryAlerts(
    val expiredBatches: List<Batch>,
    val nearExpiryBatches: List<Batch>,
    val lowStockItems: List<Item>
)

class GetInventoryAlertsUseCase(
    private val itemRepository: ItemRepository,
    private val batchRepository: BatchRepository
) {
    operator fun invoke(todayEpochDays: Long, warningWindowDays: Long = 90L): Flow<InventoryAlerts> {
        return combine(
            itemRepository.getItems(),
            batchRepository.getAllBatches()
        ) {items, batches ->
            val expiredBatches = batches.filter { it.currentStock > 0 && it.expiryDateEpochDays > todayEpochDays }
            val nearExpiryBatches = batches.filter {
                it.currentStock > 0
                it.isQuarantined == false
                it.expiryDateEpochDays > todayEpochDays
                it.expiryDateEpochDays <= todayEpochDays + warningWindowDays
            }
            val lowStockItems = items.filter {
                getSellableStock(it.id, batches, todayEpochDays) <= it.minReorderLevel
            }
            InventoryAlerts(expiredBatches, nearExpiryBatches, lowStockItems )
        }
    }

    private fun getSellableStock(itemId: Long, batches: List<Batch>, todayEpochDays: Long): Int {
        var total = 0
        for(batch in batches) {
            if(
                batch.itemId == itemId &&
                batch.currentStock > 0 &&
                batch.isQuarantined == false &&
                batch.expiryDateEpochDays > todayEpochDays
            ){
                total += batch.currentStock
            }
        }
        return total
    }
}