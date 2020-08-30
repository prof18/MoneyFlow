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
                .padding(AppMargins.regular)
                .background(Color.darkGrey)
                .cornerRadius(AppMargins.regularCornerRadius)
            
            
            VStack(alignment: .leading) {
                
                Text("self.news.title")
                    .font(.system(size: 18))
                
                Spacer()
                    .frame(height: 9)
                
                Text("self.news.getStringTime()")
                    .font(.system(size: 14))
                    .italic()
            }
    
            Spacer()
            
            Text("self.")
                .font(.system(size: 14))
                .italic()
        }
        .padding()
        .background(Color.white)
        .cornerRadius(15)
        .shadow(color: Color.black.opacity(0.2), radius: 7, x: 0, y: 2)
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
