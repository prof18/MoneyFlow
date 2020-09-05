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
            Text("Total Balance")
                .font(AppFonts.subtitle2)
            
            HStack {
                
                Text("€")
                    .font(AppFonts.h5)
                
                Text("\(balanceRecap.totalBalance)")
                    .font(AppFonts.h3)
                
            }
            
            Spacer()
                .frame(height: AppMargins.medium)
                        
            HStack {
                
                VStack(alignment: .leading) {
                    Text("-\(balanceRecap.monthlyExpenses) €")
                        .font(AppFonts.h5)
                    
                    Text("Expenses")
                        .font(AppFonts.subtitle2)
                }
                
                Spacer()
                
                VStack(alignment: .trailing) {
                    Text("+\(balanceRecap.monthlyIncome) €")
                        .font(AppFonts.h5)
                    
                    Text("Income")
                        .font(AppFonts.subtitle2)
                    
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
