//
//  HomeScreen.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI
import shared

struct HomeScreen: View {
    
    @ObservedObject var viewModel: HomeViewModel = HomeViewModel()
    
    @State private var showAddTransaction = false
    
    var body: some View {
        
        VStack {
            
            if (viewModel.homeModel is HomeModel.Loading) {
                Loader().edgesIgnoringSafeArea(.all)
            } else if (viewModel.homeModel is HomeModel.HomeState) {
                
                HomeRecap(balanceRecap: (viewModel.homeModel as! HomeModel.HomeState).balanceRecap)
                HeaderNavigator()
                
                List {
                    ForEach((viewModel.homeModel as! HomeModel.HomeState).latestTransactions, id: \.self) { transaction in
                        TransactionCard(transaction: transaction)
                            .listRowInsets(EdgeInsets())
                    }
                }
                .listStyle(PlainListStyle())
            }
        }
        .navigationTitle("Money Flow")
        .navigationBarItems(trailing: Button(action: {
            self.showAddTransaction.toggle()
        }) {
            // TODO: localize
            Text("Add Transaction")
            
        })
        .sheet(isPresented: self.$showAddTransaction) {
            AddTransactionScreen(showSheet: self.$showAddTransaction)
        }
        .onAppear {
            self.viewModel.startObserving()
        }.onDisappear {
            self.viewModel.stopObserving()
        }
        
    }
}

//struct HomeScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeScreen()
//    }
//}
