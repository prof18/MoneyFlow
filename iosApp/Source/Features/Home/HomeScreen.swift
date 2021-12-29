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
            appErrorData: $appState.errorData,
            screenErrorData: $viewModel.uiErrorData,
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
    @Binding var appErrorData: UIErrorData
    @Binding var screenErrorData: UIErrorData
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
        .onChange(of: self.screenErrorData) { errorData in
            self.appErrorData = errorData
        }
        .onChange(of: self.homeModel) { model  in
            if model is HomeModel.Error {
                let uiErrorMessage = (model as! HomeModel.Error).uiErrorMessage
                self.appErrorData = uiErrorMessage.toUIErrorData()
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
            appErrorData: .constant(UIErrorData.init()),
            screenErrorData: .constant(UIErrorData.init()),
            homeModel: .constant(model ) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )
        
        HomeScreenContent(
            reloadDatabase: .constant(false ),
            appErrorData: .constant(UIErrorData.init()),
            screenErrorData: .constant(UIErrorData.init()),
            homeModel: .constant(HomeModel.Loading()) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )
        
        HomeScreenContent(
            reloadDatabase: .constant(false ),
            appErrorData: .constant(UIErrorData(title: "An error occoured", nerdishDesc: "Error code 1012", showBanner: true )),
            screenErrorData: .constant(UIErrorData.init()),
            homeModel: .constant(HomeModel.Error(uiErrorMessage: UIErrorMessage(message: "Error!", nerdMessage: "Error code: 101"))) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )
    }
}
