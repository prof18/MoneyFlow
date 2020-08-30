//
//  HomeRecap.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI

struct HomeRecap: View {
    var body: some View {
        VStack {
            // TODO: localize
            Text("Total Balance")
                .font(AppFonts.subtitle2)
            
            HStack {
                
                Text("€")
                    .font(AppFonts.h5)
                
                Text("1234")
                    .font(AppFonts.h3)
                
            }
            
            Spacer()
                .frame(height: AppMargins.medium)
                        
            HStack {
                
                VStack(alignment: .leading) {
                    Text("-350 €")
                        .font(AppFonts.h5)
                    
                    Text("Expenses")
                        .font(AppFonts.subtitle2)
                }
                
                Spacer()
                
                VStack(alignment: .trailing) {
                    Text("+1300 €")
                        .font(AppFonts.h5)
                    
                    Text("Income")
                        .font(AppFonts.subtitle2)
                    
                }
                
            }
            
        }
        .padding()
    }
}

struct HomeRecap_Previews: PreviewProvider {
    static var previews: some View {
        HomeRecap()
    }
}
