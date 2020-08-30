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
                
                ScrollView {
                    LazyVStack {
                        ForEach(0...5, id: \.self) { _ in
                            TransactionCard()
                        }
                    }
                }
            }
            .navigationBarTitle(Text("Wallet"), displayMode: .automatic)
            .navigationBarItems(trailing: Button(action: {
                print("tapped")
            }) {
                Image(systemName: "plus.circle.fill")
                    .foregroundColor(Color.primary)
                
            })
            
            
            
        }
        
        
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
