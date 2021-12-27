package com.prof18.moneyflow.utils

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun MillisSinceEpoch.generateCurrentMonthId(): CurrentMonthID {
    val instant = Instant.fromEpochMilliseconds(this)
    val dateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val id = "${dateTime.year}${dateTime.monthNumber}"
    return id.toLong()
}

fun MillisSinceEpoch.formatDateDayMonthYear(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val dateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.dayOfMonth}/${dateTime.monthNumber}/${dateTime.year}"
}

fun Long.formatDateAllData(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val dateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.dayOfMonth}/${dateTime.monthNumber}/${dateTime.year} - ${dateTime.hour}:${dateTime.minute}"

}

fun Throwable.logError(moneyFlowError: MoneyFlowError, message: String? = null) {
    val logMessage = buildString {
        append("Error code: ${moneyFlowError.code}")
        message?.let {
            append(" - Details: $message")
        }
    }
    Logger.w(this) { logMessage }
}