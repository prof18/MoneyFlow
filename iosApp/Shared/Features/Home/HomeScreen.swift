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
                Loader()
            } else if (viewModel.homeModel is HomeModel.HomeState) {
                
                let homeState = (viewModel.homeModel as! HomeModel.HomeState)
                
                HomeRecap(balanceRecap: homeState.balanceRecap)
                HeaderNavigator()
                
                if homeState.latestTransactions.isEmpty {
                    Spacer()
                    VStack {
                        Text("¯\\_(ツ)_/¯")
                            .padding(.bottom, AppMargins.small)
                        Text("Your wallet is empty")
                    }
                    Spacer()
                } else {
                    
                    List {
                        ForEach(homeState.latestTransactions, id: \.self) { transaction in
                            TransactionCard(transaction: transaction)
                                .listRowInsets(EdgeInsets())
                                .contextMenu {
                                    Button(action: {
                                        // change country setting
                                        viewModel.deleteTransaction(transactionId: transaction.id)
                                    }) {
                                        Text("Delete")
                                        Image(systemName: "trash")
                                    }
                                }
                        }
                    }
                    .listStyle(PlainListStyle())
                }
            }
        }
        .navigationTitle("Home")
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
