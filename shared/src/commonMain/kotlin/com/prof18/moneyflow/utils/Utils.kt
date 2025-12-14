package com.prof18.moneyflow.utils

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

// TODO: write tests for some of these functions
internal data class MonthRange(
    val startMillis: Long,
    val endMillis: Long,
)

private val dayMonthYearFormatter = LocalDate.Format {
    day()
    char('/')
    monthNumber()
    char('/')
    year()
}

internal fun Long.formatDateDayMonthYear(
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): String {
    val dateTime = toLocalDateTime(timeZone)
    return dateTime.date.format(dayMonthYearFormatter)
}

internal fun Long.toMonthRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): MonthRange {
    val dateTime: LocalDateTime = toLocalDateTime(timeZone)
    val startDate = LocalDate(dateTime.year, dateTime.month, 1)
    val startMillis = startDate.atStartOfDayIn(timeZone).toEpochMilliseconds()
    val endMillis = startDate.plus(DatePeriod(months = 1))
        .atStartOfDayIn(timeZone)
        .toEpochMilliseconds()
    return MonthRange(startMillis = startMillis, endMillis = endMillis)
}

internal fun currentMonthRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): MonthRange =
    Clock.System.now().toEpochMilliseconds().toMonthRange(timeZone)

internal fun Long.toLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)

internal fun Throwable.logError(moneyFlowError: MoneyFlowError, message: String? = null) {
    val logMessage = buildString {
        append("Error code: ${moneyFlowError.code}")
        message?.let {
            append(" - Details: $message")
        }
    }
    Logger.w(this) { logMessage }
}
