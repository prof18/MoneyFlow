package com.prof18.moneyflow.database.default

import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.presentation.model.CategoryIcon
import kotlin.time.Clock

internal data class DefaultCategory(
    val name: String,
    val type: TransactionType,
    val iconName: String,
    val createdAtMillis: Long,
)

private val seedTimestamp = Clock.System.now().toEpochMilliseconds()

// TODO: localize and set category id directly instead of the name
internal val defaultCategories = listOf(
    DefaultCategory(
        name = "Accessories",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BLACK_TIE.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Bar",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_COCKTAIL_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Books",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BOOK_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Clothing",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_TSHIRT_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Eating Out",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HAMBURGER_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Education",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GRADUATION_CAP_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Electronics",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_LAPTOP_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Entertainment",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BOWLING_BALL_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Extra Income",
        type = TransactionType.INCOME,
        iconName = CategoryIcon.IC_DONATE_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Family",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HOME_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Fees",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_FILE_INVOICE_DOLLAR_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Film",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_FILM_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Food & Beverage",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_DRUMSTICK_BITE_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Footwear",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_SOCKS_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Gifts",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GIFT_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Hairdresser",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_CUT_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Hotel",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BUILDING.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Internet Service",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_SERVER_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Love and Friends",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_USER_FRIENDS_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Medical",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HOSPITAL.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Music",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HEADPHONES_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Other",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_DOLLAR_SIGN.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Parking Fees",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_PARKING_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Petrol",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_CHARGING_STATION_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Phone",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_PHONE_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Plane",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_PLANE_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Salary",
        type = TransactionType.INCOME,
        iconName = CategoryIcon.IC_MONEY_CHECK_ALT_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Selling",
        type = TransactionType.INCOME,
        iconName = CategoryIcon.IC_DOLLY_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Software",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_FILE_CODE.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Sport",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BASKETBALL_BALL_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Train",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_TRAIN_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Transportation",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BUS_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Travel",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GLOBE_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Videogames",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GAMEPAD_SOLID.iconName,
        createdAtMillis = seedTimestamp,
    ),
)
