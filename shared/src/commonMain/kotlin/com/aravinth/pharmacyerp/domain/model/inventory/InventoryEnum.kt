package com.aravinth.pharmacyerp.domain.model.inventory

enum class DrugSchedule {
    SCHEDULE_H,
    SCHEDULE_H1,
    SCHEDULE_X,
    ORDINARY,
    OTC
}

enum class Formulation {
    ORAL_TABLET,
    ORAL_CAPSULE,
    SR_XR_TABLET,
    DISPERSIBLE_TABLET,
    ORAL_LIQUID,
    ORAL_DROPS,
    EYE_EAR_DROPS,
    NASAL_SPRAY,
    INJ_VIAL,
    INJ_AMPOULE,
    IV_INFUSION,
    PFS,
    TOPICAL_CREAM,
    TOPICAL_LOTION,
    DUSTING_POWDER,
    INHALER,
    RESPULE,
    ROTACAP,
    SUPPOSITORY,
    PATCH,
    SURGICAL_DEVICE
}

enum class Storage {
   AMBIENT,
    COOL,
    COLD_CHAIN,
    FREEZER
}

enum class LightProtection {
    NONE,
    LIGHT_PROTECTED,
    CIRITICAL_LIGHT_SENSITIVE
}

enum class DrugType {
    ANTI_INFECTIVE,
    CARDIOVASCULAR,
    ANTIDIABETIC,
    GASTROINTESTINAL,
    RESPIRATORY,
    CNS_AGENTS,
    DERMATOLOGY,
    NUTRACEUTICAL,
    AYURVEDIC,
    HOMEOPATHIC
}

enum class UnitMeasurement {
    STRIP,
    BOX,
    PCS,
    CAPSULE,
    BOTTLE,
    VIAL,
    TUBE,
    PAIR,
    G
}

enum class Rack {
    A1,
    A2,
    B1,
    B2,
    C1,
    C2,
    D1,
    D2
}

enum class Shelf {
    S01,
    S02,
    S03,
    S04,
    S05
}

enum class EventType {
    POS_BILLING,
    STOCK_INWARD,
    PRICE_SHIFT,
    ADJUSTMENT
}