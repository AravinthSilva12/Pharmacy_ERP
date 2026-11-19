package com.aravinth.pharmacyerp.data.repository.inventory

import com.aravinth.pharmacyerp.domain.model.inventory.Item
import com.aravinth.pharmacyerp.domain.repository.inventory.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryItemRepository(initialItems: List<Item> = emptyList()) : ItemRepository {

    private val _items = MutableStateFlow(initialItems)

   override fun getItems(): Flow<List<Item>> {
        return _items.asStateFlow()
    }

    override fun getItemById(id: Long): Flow<Item?> {
        return _items.map { list ->
            list.find {it.id == id}
        }
    }

    override fun searchItems(query: String): Flow<List<Item>> {
        return _items.map {list ->
            if(query.isBlank()) {
               list
            } else{
                list.filter {
                    it.medicine.contains(query, ignoreCase = true) ||
                    it.composition.contains(query, ignoreCase = true) ||
                    it.manufacturer.contains(query, ignoreCase = true) ||
                    it.hsnCode.contains(query, ignoreCase = true) ||
                    it.eanBarcode?.contains(query, ignoreCase = true) == true
                }
            }
        }
    }

    override suspend fun upsertItem(item: Item): Long {
        var savedId = item.id
        _items.update {currentList ->
        if (savedId == 0L) {
           savedId = (currentList.maxOfOrNull { it.id} ?: 0L) + 1L
           currentList + item.copy(id = savedId)
        } else {
             currentList.map { existing ->
                 if(existing.id == savedId) item else existing
             }
        }
      }
        return savedId
    }

    override suspend fun deleteItem(id: Long): Unit {
         _items.update {currentList ->
             currentList.filterNot { it.id == id }
         }
    }
}