package com.aravinth.pharmacyerp.domain.model.inventory


data class StockLog(
    val id: Long = 0L,
    val itemId: Long = 0L,
    val timeStampEpochMillis: Long,
    val stockEventType: EventType,
    val operatorName: String,
    val voucherRef: String,
    val batchNumber: String,
    val quantityShift: Double
)