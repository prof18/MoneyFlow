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
            // TODO: localize
            HStack {
                
                Text("€")
                    .font(AppFonts.h5)
                
                Text("\(balanceRecap.totalBalance.formatTwoDigit())")
                    .font(AppFonts.h3)
                
            }
            
            Text("Total Balance")
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
                        
                        Text("Income")
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
                        
                        Text("Expenses")
                            .font(AppFonts.subtitle2)
                    }
                    
                }
                
                
                
                
                
            }
            
        }
        .padding()
    }
}

// TODO: restore preview
//struct HomeRecap_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeRecap()
//    }
//}
