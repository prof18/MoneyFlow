package com.prof18.moneyflow.database.default

import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.db.CategoryTable

// TODO: localize?

internal val defaultCategories = listOf(

    CategoryTable(
        id = 0,
        name = "Accessories",
        type = TransactionType.OUTCOME,
        iconName = "ic_black_tie",
    ),

    CategoryTable(
        id = 1,
        name = "Bar",
        type = TransactionType.OUTCOME,
        iconName = "ic_cocktail_solid",
    ),

    CategoryTable(
        id = 2,
        name = "Books",
        type = TransactionType.OUTCOME,
        iconName = "ic_book_solid",
    ),

    CategoryTable(
        id = 3,
        name = "Clothing",
        type = TransactionType.OUTCOME,
        iconName = "ic_tshirt_solid",
    ),

    CategoryTable(
        id = 4,
        name = "Eating Out",
        type = TransactionType.OUTCOME,
        iconName = "ic_hamburger_solid",
    ),

    CategoryTable(
        id = 5,
        name = "Education",
        type = TransactionType.OUTCOME,
        iconName = "ic_graduation_cap_solid",
    ),

    CategoryTable(
        id = 6,
        name = "Electronics",
        type = TransactionType.OUTCOME,
        iconName = "ic_laptop_solid",
    ),

    CategoryTable(
        id = 7,
        name = "Entertainment",
        type = TransactionType.OUTCOME,
        iconName = "ic_bowling_ball_solid",
    ),

    CategoryTable(
        id = 8,
        name = "Extra Income",
        type = TransactionType.INCOME,
        iconName = "ic_donate_solid",
    ),

    CategoryTable(
        id = 9,
        name = "Family",
        type = TransactionType.OUTCOME,
        iconName = "ic_home_solid",
    ),

    CategoryTable(
        id = 10,
        name = "Fees",
        type = TransactionType.OUTCOME,
        iconName = "ic_file_invoice_dollar_solid",
    ),

    CategoryTable(
        id = 11,
        name = "Film",
        type = TransactionType.OUTCOME,
        iconName = "ic_film_solid",
    ),

    CategoryTable(
        id = 12,
        name = "Food & Beverage",
        type = TransactionType.OUTCOME,
        iconName = "ic_drumstick_bite_solid",
    ),

    CategoryTable(
        id = 13,
        name = "Footwear",
        type = TransactionType.OUTCOME,
        iconName = "ic_socks_solid",
    ),

    CategoryTable(
        id = 14,
        name = "Gifts",
        type = TransactionType.OUTCOME,
        iconName = "ic_gift_solid",
    ),

    CategoryTable(
        id = 15,
        name = "Hairdresser",
        type = TransactionType.OUTCOME,
        iconName = "ic_cut_solid",
    ),

    CategoryTable(
        id = 16,
        name = "Hotel",
        type = TransactionType.OUTCOME,
        iconName = "ic_building",
    ),

    CategoryTable(
        id = 17,
        name = "Internet Service",
        type = TransactionType.OUTCOME,
        iconName = "ic_server_solid",
    ),

    CategoryTable(
        id = 18,
        name = "Love and Friends",
        type = TransactionType.OUTCOME,
        iconName = "ic_user_friends_solid",
    ),

    CategoryTable(
        id = 19,
        name = "Medical",
        type = TransactionType.OUTCOME,
        iconName = "ic_hospital",
    ),

    CategoryTable(
        id = 20,
        name = "Music",
        type = TransactionType.OUTCOME,
        iconName = "ic_headphones_solid",
    ),

    CategoryTable(
        id = 21,
        name = "Other",
        type = TransactionType.OUTCOME,
        iconName = "ic_dollar_sign",
    ),

    CategoryTable(
        id = 22,
        name = "Parking Fees",
        type = TransactionType.OUTCOME,
        iconName = "ic_parking_solid",
    ),

    CategoryTable(
        id = 23,
        name = "Petrol",
        type = TransactionType.OUTCOME,
        iconName = "ic_charging_station_solid",
    ),

    CategoryTable(
        id = 24,
        name = "Phone",
        type = TransactionType.OUTCOME,
        iconName = "ic_phone_solid",
    ),

    CategoryTable(
        id = 25,
        name = "Plane",
        type = TransactionType.OUTCOME,
        iconName = "ic_plane_solid",
    ),

    CategoryTable(
        id = 26,
        name = "Salary",
        type = TransactionType.INCOME,
        iconName = "ic_money_check_alt_solid",
    ),

    CategoryTable(
        id = 27,
        name = "Selling",
        type = TransactionType.INCOME,
        iconName = "ic_dolly_solid",
    ),

    CategoryTable(
        id = 28,
        name = "Software",
        type = TransactionType.OUTCOME,
        iconName = "ic_file_code",
    ),

    CategoryTable(
        id = 29,
        name = "Sport",
        type = TransactionType.OUTCOME,
        iconName = "ic_basketball_ball_solid",
    ),

    CategoryTable(
        id = 30,
        name = "Train",
        type = TransactionType.OUTCOME,
        iconName = "ic_train_solid",
    ),

    CategoryTable(
        id = 31,
        name = "Transportation",
        type = TransactionType.OUTCOME,
        iconName = "ic_bus_solid",
    ),

    CategoryTable(
        id = 32,
        name = "Travel",
        type = TransactionType.OUTCOME,
        iconName = "ic_globe_solid",
    ),

    CategoryTable(
        id = 33,
        name = "Videogames",
        type = TransactionType.OUTCOME,
        iconName = "ic_gamepad_solid",
    ),
)
