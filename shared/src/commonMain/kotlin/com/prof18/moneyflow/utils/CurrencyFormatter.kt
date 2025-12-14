package com.prof18.moneyflow.utils

import com.prof18.moneyflow.domain.entities.CurrencyConfig
import kotlin.math.abs

// TODO: write tests for all these functions
fun Long.formatAsCurrency(config: CurrencyConfig): String {
    val factor = tenFactor(config.decimalPlaces)
    val absoluteValue = abs(this)
    val wholePart = absoluteValue / factor
    val decimalPart = absoluteValue % factor
    val decimalString = if (config.decimalPlaces == 0) {
        ""
    } else {
        ".${decimalPart.toString().padStart(config.decimalPlaces, '0')}"
    }

    val sign = if (this < 0) "-" else ""
    return "$sign${config.symbol}$wholePart$decimalString"
}

@Suppress("ReturnCount")
fun String.toAmountCents(config: CurrencyConfig): Long? {
    val normalized = trim()
    if (normalized.isEmpty()) return null

    val sanitized = normalized.replace(',', '.')
    val parts = sanitized.split(".")
    if (parts.size > 2) return null

    val signMultiplier = if (sanitized.startsWith("-")) -1 else 1
    val wholePartText = parts[0].removePrefix("-")
    val wholePart = wholePartText.toLongOrNull()?.let { abs(it) } ?: return null

    val decimalText = if (parts.size == 2) parts[1] else ""
    if (decimalText.length > config.decimalPlaces) return null

    val decimalValue = if (config.decimalPlaces == 0) {
        0L
    } else {
        decimalText.padEnd(config.decimalPlaces, '0')
            .take(config.decimalPlaces)
            .toLongOrNull()
            ?: return null
    }

    val factor = tenFactor(config.decimalPlaces)
    return abs(signMultiplier * ((wholePart * factor) + decimalValue))
}

@Suppress("MagicNumber")
private fun tenFactor(decimalPlaces: Int): Long {
    var factor = 1L
    repeat(decimalPlaces) {
        factor *= 10
    }
    return factor
}
