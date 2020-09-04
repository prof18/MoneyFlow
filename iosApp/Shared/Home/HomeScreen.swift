//
//  HomeScreen.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI
import shared

struct HomeScreen: View, HomeView {
    
    var body: some View {
        
        NavigationView {
            
            VStack {
                
                HomeRecap()
                HeaderNavigator()
                
                // TODO: add lazy list
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
