//
//  TransactionCard.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI

struct TransactionCard: View {
    var body: some View {
        HStack() {
            
            Image("hamburger")
                .padding(AppMargins.small)
                .background(Color.darkGrey)
                .cornerRadius(AppMargins.regular)
                .padding(AppMargins.regular)
            
            
            VStack(alignment: .leading) {
                
                Text("Dinner with friends")
                    .font(AppFonts.subtitle1)
                    .padding(.top, AppMargins.regular)
                
                
                Text("21 Jan 2020")
                    .font(AppFonts.caption)
                    .padding(.bottom, AppMargins.regular)

            }
    
            Spacer()
            
            Text("-1500 €")
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

struct TransactionCard_Previews: PreviewProvider {
    static var previews: some View {
        TransactionCard()
    }
}
