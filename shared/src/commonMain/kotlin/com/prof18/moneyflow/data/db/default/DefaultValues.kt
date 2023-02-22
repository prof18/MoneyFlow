package com.prof18.moneyflow.data.db.default

import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.presentation.model.CategoryIcon

// TODO: localize?

internal val defaultCategories = listOf(

    CategoryTable(
        id = 0,
        name = "Accessories",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BLACK_TIE.iconName,
    ),

    CategoryTable(
        id = 1,
        name = "Bar",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_COCKTAIL_SOLID.iconName,
    ),

    CategoryTable(
        id = 2,
        name = "Books",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BOOK_SOLID.iconName,
    ),

    CategoryTable(
        id = 3,
        name = "Clothing",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_TSHIRT_SOLID.iconName,
    ),

    CategoryTable(
        id = 4,
        name = "Eating Out",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HAMBURGER_SOLID.iconName,
    ),

    CategoryTable(
        id = 5,
        name = "Education",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GRADUATION_CAP_SOLID.iconName,
    ),

    CategoryTable(
        id = 6,
        name = "Electronics",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_LAPTOP_SOLID.iconName,
    ),

    CategoryTable(
        id = 7,
        name = "Entertainment",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BOWLING_BALL_SOLID.iconName,
    ),

    CategoryTable(
        id = 8,
        name = "Extra Income",
        type = TransactionType.INCOME,
        iconName = CategoryIcon.IC_DONATE_SOLID.iconName,
    ),

    CategoryTable(
        id = 9,
        name = "Family",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HOME_SOLID.iconName,
    ),

    CategoryTable(
        id = 10,
        name = "Fees",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_FILE_INVOICE_DOLLAR_SOLID.iconName,
    ),

    CategoryTable(
        id = 11,
        name = "Film",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_FILM_SOLID.iconName,
    ),

    CategoryTable(
        id = 12,
        name = "Food & Beverage",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_DRUMSTICK_BITE_SOLID.iconName,
    ),

    CategoryTable(
        id = 13,
        name = "Footwear",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_SOCKS_SOLID.iconName,
    ),

    CategoryTable(
        id = 14,
        name = "Gifts",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GIFT_SOLID.iconName,
    ),

    CategoryTable(
        id = 15,
        name = "Hairdresser",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_CUT_SOLID.iconName,
    ),

    CategoryTable(
        id = 16,
        name = "Hotel",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BUILDING.iconName,
    ),

    CategoryTable(
        id = 17,
        name = "Internet Service",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_SERVER_SOLID.iconName,
    ),

    CategoryTable(
        id = 18,
        name = "Love and Friends",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_USER_FRIENDS_SOLID.iconName,
    ),

    CategoryTable(
        id = 19,
        name = "Medical",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HOSPITAL.iconName,
    ),

    CategoryTable(
        id = 20,
        name = "Music",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_HEADPHONES_SOLID.iconName,
    ),

    CategoryTable(
        id = 21,
        name = "Other",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_DOLLAR_SIGN.iconName,
    ),

    CategoryTable(
        id = 22,
        name = "Parking Fees",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_PARKING_SOLID.iconName,
    ),

    CategoryTable(
        id = 23,
        name = "Petrol",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_CHARGING_STATION_SOLID.iconName,
    ),

    CategoryTable(
        id = 24,
        name = "Phone",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_PHONE_SOLID.iconName,
    ),

    CategoryTable(
        id = 25,
        name = "Plane",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_PLANE_SOLID.iconName,
    ),

    CategoryTable(
        id = 26,
        name = "Salary",
        type = TransactionType.INCOME,
        iconName = CategoryIcon.IC_MONEY_CHECK_ALT_SOLID.iconName,
    ),

    CategoryTable(
        id = 27,
        name = "Selling",
        type = TransactionType.INCOME,
        iconName = CategoryIcon.IC_DOLLY_SOLID.iconName,
    ),

    CategoryTable(
        id = 28,
        name = "Software",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_FILE_CODE.iconName,
    ),

    CategoryTable(
        id = 29,
        name = "Sport",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BASKETBALL_BALL_SOLID.iconName,
    ),

    CategoryTable(
        id = 30,
        name = "Train",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_TRAIN_SOLID.iconName,
    ),

    CategoryTable(
        id = 31,
        name = "Transportation",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_BUS_SOLID.iconName,
    ),

    CategoryTable(
        id = 32,
        name = "Travel",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GLOBE_SOLID.iconName,
    ),

    CategoryTable(
        id = 33,
        name = "Videogames",
        type = TransactionType.OUTCOME,
        iconName = CategoryIcon.IC_GAMEPAD_SOLID.iconName,
    ),
)
