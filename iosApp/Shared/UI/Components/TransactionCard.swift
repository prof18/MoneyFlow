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
        HStack() {
            
            DMImage(imageName: transaction.icon.iconName)
                .padding(AppMargins.small)
                .background(Color.primary)
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
            
            // TODO: fix this
            Text("\(transaction.amount.formatTwoDigit()) €")
                .font(AppFonts.body1)
                .padding(.bottom, AppMargins.regular)
                .padding(.top, AppMargins.regular)
//                .padding(.trailing, AppMargins.regular)


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
