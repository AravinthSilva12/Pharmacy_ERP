package com.aravinth.pharmacyerp.domain.repository.inventory

import com.aravinth.pharmacyerp.domain.model.inventory.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository
{
    fun getItems(): Flow<List<Item>>
    fun getItemById(id: Long): Flow<Item?>
    fun searchItems(query: String): Flow<List<Item>>
    suspend fun upsertItem(item: Item): Long
    suspend fun deleteItem(id: Long): Unit
}