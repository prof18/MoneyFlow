//
//  TransactionCard.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI
import shared

struct TransactionCard: View {
    
    var transaction: shared.Transaction
    
    var body: some View {
        HStack() {
            
            Image("hamburger")
                .padding(AppMargins.small)
                .background(Color.darkGrey)
                .cornerRadius(AppMargins.regular)
                .padding(AppMargins.regular)
            
            
            VStack(alignment: .leading) {
                
                Text(transaction.title)
                    .font(AppFonts.subtitle1)
                    .padding(.top, AppMargins.regular)
                
                
                Text(transaction.formattedDate)
                    .font(AppFonts.caption)
                    .padding(.bottom, AppMargins.regular)

            }
    
            Spacer()
            
            // TODO: fix this
            Text("-\(transaction.amount) €")
                .font(AppFonts.body1)
                .padding(.bottom, AppMargins.regular)
                .padding(.top, AppMargins.regular)
                .padding(.trailing, AppMargins.regular)


        }
        
        .onTapGesture {
            // TODO
        }
    }
}

// TODO: restore preview
//struct TransactionCard_Previews: PreviewProvider {
//    static var previews: some View {
//        TransactionCard()
//    }
//}
