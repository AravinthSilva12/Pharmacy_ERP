package com.aravinth.pharmacyerp.domain.model.inventory


data class Batch (
      val id: Long = 0L,
      val itemId: Long = 0L,
      val batchNumber: String,
      val expiryDateEpochDays: Long,
      val currentStock: Int,
      val vendor: String,
      val priceToRetailer: Double,
      val priceToStockist: Double,
      val landedCost: Double,
      val maximumRetailPrice: Double,
      val tradeDiscountPercentage: Double = 0.0,
      val cashDiscountPercentage: Double,
      val freightCost: Double = 0.0,
      val belongsToNlem: Boolean = false,
      val isPriceCapped: Boolean = false,
      val shelfLife: Int,
      )