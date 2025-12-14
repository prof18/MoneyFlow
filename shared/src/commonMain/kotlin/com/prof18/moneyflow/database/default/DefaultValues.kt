package com.prof18.moneyflow.database.default

import com.prof18.moneyflow.database.model.TransactionType
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
        iconName = "ic_black_tie",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Bar",
        type = TransactionType.OUTCOME,
        iconName = "ic_cocktail_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Books",
        type = TransactionType.OUTCOME,
        iconName = "ic_book_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Clothing",
        type = TransactionType.OUTCOME,
        iconName = "ic_tshirt_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Eating Out",
        type = TransactionType.OUTCOME,
        iconName = "ic_hamburger_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Education",
        type = TransactionType.OUTCOME,
        iconName = "ic_graduation_cap_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Electronics",
        type = TransactionType.OUTCOME,
        iconName = "ic_laptop_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Entertainment",
        type = TransactionType.OUTCOME,
        iconName = "ic_bowling_ball_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Extra Income",
        type = TransactionType.INCOME,
        iconName = "ic_donate_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Family",
        type = TransactionType.OUTCOME,
        iconName = "ic_home_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Fees",
        type = TransactionType.OUTCOME,
        iconName = "ic_file_invoice_dollar_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Film",
        type = TransactionType.OUTCOME,
        iconName = "ic_film_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Food & Beverage",
        type = TransactionType.OUTCOME,
        iconName = "ic_drumstick_bite_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Footwear",
        type = TransactionType.OUTCOME,
        iconName = "ic_socks_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Gifts",
        type = TransactionType.OUTCOME,
        iconName = "ic_gift_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Hairdresser",
        type = TransactionType.OUTCOME,
        iconName = "ic_cut_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Hotel",
        type = TransactionType.OUTCOME,
        iconName = "ic_building",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Internet Service",
        type = TransactionType.OUTCOME,
        iconName = "ic_server_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Love and Friends",
        type = TransactionType.OUTCOME,
        iconName = "ic_user_friends_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Medical",
        type = TransactionType.OUTCOME,
        iconName = "ic_hospital",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Music",
        type = TransactionType.OUTCOME,
        iconName = "ic_headphones_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Other",
        type = TransactionType.OUTCOME,
        iconName = "ic_dollar_sign",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Parking Fees",
        type = TransactionType.OUTCOME,
        iconName = "ic_parking_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Petrol",
        type = TransactionType.OUTCOME,
        iconName = "ic_charging_station_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Phone",
        type = TransactionType.OUTCOME,
        iconName = "ic_phone_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Plane",
        type = TransactionType.OUTCOME,
        iconName = "ic_plane_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Salary",
        type = TransactionType.INCOME,
        iconName = "ic_money_check_alt_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Selling",
        type = TransactionType.INCOME,
        iconName = "ic_dolly_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Software",
        type = TransactionType.OUTCOME,
        iconName = "ic_file_code",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Sport",
        type = TransactionType.OUTCOME,
        iconName = "ic_basketball_ball_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Train",
        type = TransactionType.OUTCOME,
        iconName = "ic_train_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Transportation",
        type = TransactionType.OUTCOME,
        iconName = "ic_bus_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Travel",
        type = TransactionType.OUTCOME,
        iconName = "ic_globe_solid",
        createdAtMillis = seedTimestamp,
    ),
    DefaultCategory(
        name = "Videogames",
        type = TransactionType.OUTCOME,
        iconName = "ic_gamepad_solid",
        createdAtMillis = seedTimestamp,
    ),
)
