package com.aravinth.pharmacyerp.domain.model.inventory

data class Item(
    val id: Long = 0L,
    val medicine: String,
    val composition: String,
    val manufacturer: String,
    val schedule: DrugSchedule,
    val medicineType: DrugType,
    val formulation: Formulation,
    val storage: Storage,
    val lightProtection: LightProtection,
    val measurementUnit: UnitMeasurement,
    val hsnCode: String,
    val eanBarcode: String? = null,
    val rack: Rack,
    val shelf: Shelf,
    val minSafetyStock: Int,
    val minReorderLevel: Int,
    val maxHoldingGap: Int,
    val eoq: Int,
    val gstSlabPercentage: Double
  ) {
    val cgstPercentage: Double get() = gstSlabPercentage / 2.0
    val sgstPercentage: Double get() = gstSlabPercentage / 2.0
}