//
//  HomeScreen.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI

struct HomeScreen: View {
    
    var body: some View {
        
        NavigationView {
            
            VStack {
                
                HomeRecap()
                HeaderNavigator()
                
                List {
                    ForEach(0...5, id: \.self) { _ in
                        TransactionCard()
                            .listRowInsets(EdgeInsets())
                    }
                }
                .listStyle(PlainListStyle())
                
            }
            .navigationBarTitle(Text("Wallet"), displayMode: .automatic)
            .navigationBarItems(trailing: Button(action: {
                print("tapped")
            }) {
                Text("Add transaction")
                
            })
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
