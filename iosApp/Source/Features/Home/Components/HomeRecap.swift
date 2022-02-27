//
//  HomeRecap.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI
import shared

struct HomeRecap: View {

    var balanceRecap: BalanceRecap

    var body: some View {
        VStack {

            HStack {
                // TODO: inject currency?
                Text("€")
                    .font(AppFonts.h5)

                Text("\(balanceRecap.totalBalance.formatTwoDigit())")
                    .font(AppFonts.h3)

            }

            Text("total_balance".localized)
                .font(AppFonts.subtitle2)

            Spacer()
                .frame(height: AppMargins.medium)

            HStack {

                HStack {

                    UpArrowCircleIcon(size: 28)
                        .padding(.trailing, AppMargins.small)

                    VStack(alignment: .leading) {
                        Text("+\(balanceRecap.monthlyIncome.formatTwoDigit()) €")
                            .font(AppFonts.h5)

                        Text("transaction_type_income".localized)
                            .font(AppFonts.subtitle2)

                    }

                }

                Spacer()

                HStack {

                    DownArrowCircleIcon(size: 28)
                        .padding(.trailing, AppMargins.small)

                    VStack(alignment: .trailing) {
                        Text("-\(balanceRecap.monthlyExpenses.formatTwoDigit()) €")
                            .font(AppFonts.h5)

                        Text("transaction_type_outcome".localized)
                            .font(AppFonts.subtitle2)
                    }
                }
            }

        }
        .padding()
    }
}

struct HomeRecap_Previews: PreviewProvider {

    static let balanceRecap = BalanceRecap(totalBalance: 100, monthlyIncome: 150, monthlyExpenses: 50)

    static var previews: some View {
        HomeRecap(balanceRecap: balanceRecap)
    }
}
