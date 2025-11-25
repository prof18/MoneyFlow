//
//  TransactionCard.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI
import shared

struct TransactionCard: View {

    var transaction: MoneyTransaction

    var body: some View {
        HStack {

            DMImage(imageName: transaction.icon.iconName)
                .padding(AppMargins.small)
                .background(Color.primaryColor)
                .cornerRadius(AppMargins.regularCornerRadius)
                .padding(.vertical, AppMargins.regular)
                .padding(.trailing, AppMargins.regular)

            VStack(alignment: .leading) {

                Text(transaction.title)
                    .font(AppFonts.subtitle1)
                    .padding(.top, AppMargins.regular)

                Text(transaction.formattedDate)
                    .font(AppFonts.caption)
                    .padding(.bottom, AppMargins.regular)

            }

            Spacer()

            if transaction.type == .income {
                UpArrowCircleIcon(size: 22)
                    .padding(.trailing, AppMargins.small)
            } else {
                DownArrowCircleIcon(size: 22)
                    .padding(.trailing, AppMargins.small)
            }

            // TODO: inject currency?
            Text("\(transaction.amount.formatTwoDigit()) €")
                .font(AppFonts.body1)
                .padding(.bottom, AppMargins.regular)
                .padding(.top, AppMargins.regular)
        }

        .onTapGesture {
            // TODO: handle click on transaction?
        }
    }
}

struct TransactionCard_Previews: PreviewProvider {

    static let transactionIncome = MoneyTransaction(
        id: 1,
        title: "Salary",
        icon: CategoryIcon.icMoneyCheckAltSolid,
        amount: 100,
        type: .income,
        milliseconds: 12345,
        formattedDate: "10/10/2021"
    )

    static let transactionExpense = MoneyTransaction(
        id: 2,
        title: "Food",
        icon: CategoryIcon.icHamburgerSolid,
        amount: 20,
        type: .expense,
        milliseconds: 12345,
        formattedDate: "10/10/2021"
    )

    static var previews: some View {
        TransactionCard(transaction: transactionIncome)

        TransactionCard(transaction: transactionExpense)
    }
}
