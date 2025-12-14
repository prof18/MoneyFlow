package com.prof18.moneyflow.domain.entities

internal data class CurrencyConfig(
    val code: String,
    val symbol: String,
    val decimalPlaces: Int,
)
