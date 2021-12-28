//
//  HomeScreen.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI
import shared

struct HomeScreen: View {
    
    @EnvironmentObject var appState: AppState
    @StateObject var viewModel: HomeViewModel = HomeViewModel()
    
    var body: some View {
        HomeScreenContent(
            reloadDatabase: $appState.reloadDatabase,
            errorData: $appState.errorData,
            homeModel: $viewModel.homeModel,
            onAppear: { viewModel.startObserving() },
            deleteTransaction: { transactionId in
                viewModel.deleteTransaction(transactionId: transactionId)
            }
        )
    }
}

struct HomeScreenContent: View  {
    
    @Binding var reloadDatabase: Bool
    @Binding var errorData: UIErrorData
    @Binding var homeModel: HomeModel
    
    let onAppear  : () -> Void
    let deleteTransaction: (Int64) -> Void
    
    @State private var showAddTransaction = false
    
    var body: some View {
        
        VStack {
            
            // TODO: cover the error case here!!
            if (homeModel is HomeModel.Loading) {
                Loader()
            } else if (homeModel is HomeModel.HomeState) {
                
                let homeState = (homeModel as! HomeModel.HomeState)
                
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
                                        deleteTransaction(transaction.id )
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
        .navigationTitle("My Wallet")
        .navigationBarItems(/*leading: refreshButton,*/
            trailing: Button(
                action: {
                    self.showAddTransaction.toggle()
                }) {
                    // TODO: localize
                    Image(systemName: "plus")
                }
        )
        .sheet(isPresented: self.$showAddTransaction) {
            AddTransactionScreen(showSheet: self.$showAddTransaction)
        }
        .onAppear {
            self.onAppear()
        }
        .onChange(of: self.reloadDatabase) { value  in
            if value {
                print("Reload received")
                // Start observing again without stopping, because the use case has been already restored
                onAppear()
                self.reloadDatabase = false
            }
        }
        .onChange(of: self.homeModel) { model  in
            if model is HomeModel.Error {
                // TODO: call an error mapper and show the error
                //                self.errorData = UIErrorData(title: "This is a sample error, just to test", nerdishDesc: "Error code 101", showBanner: true)
            }
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static let model = HomeModel.HomeState(
        balanceRecap: BalanceRecap(totalBalance: 100, monthlyIncome: 150, monthlyExpenses: 50),
        latestTransactions: [
            MoneyTransaction(id: 1, title: "Transaction", icon: CategoryIcon.icAddressBook, amount: 50, type: TransactionTypeUI.expense, milliseconds: 123456, formattedDate: "20/10/21")
        ]
    )
    
    static var previews: some View {
        HomeScreenContent(
            reloadDatabase: .constant(false ),
            errorData: .constant(UIErrorData.init()),
            homeModel: .constant(model ) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )
        
        HomeScreenContent(
            reloadDatabase: .constant(false ),
            errorData: .constant(UIErrorData.init()),
            homeModel: .constant(HomeModel.Loading()) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )
        
        HomeScreenContent(
            reloadDatabase: .constant(false ),
            errorData: .constant(UIErrorData(title: "An error occoured", nerdishDesc: "Error code 1012", showBanner: true )),
            homeModel: .constant(HomeModel.Error(error: MoneyFlowError.GetMoneySummary(throwable: KotlinThrowable()))) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )
    }
}
