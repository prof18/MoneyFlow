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
            appErrorData: $appState.snackbarData,
            screenErrorData: $viewModel.snackbarData,
            homeModel: $viewModel.homeModel,
            onAppear: { viewModel.startObserving() },
            deleteTransaction: { transactionId in
                viewModel.deleteTransaction(transactionId: transactionId)
            }
        )
    }
}

struct HomeScreenContent: View {

    @Binding var appErrorData: SnackbarData
    @Binding var screenErrorData: SnackbarData
    @Binding var homeModel: HomeModel

    let onAppear  : () -> Void
    let deleteTransaction: (Int64) -> Void

    @State private var showAddTransaction = false

    var body: some View {

        VStack {

            if (homeModel is HomeModel.Loading) {
                ProgressView()
            } else if let error = homeModel as? HomeModel.Error {
                ErrorView(uiErrorMessage: error.uiErrorMessage)
            } else if let homeState = homeModel as? HomeModel.HomeState {

                HomeRecap(balanceRecap: homeState.balanceRecap)
                HeaderNavigator()

                if homeState.latestTransactions.isEmpty {
                    Spacer()
                    VStack {
                        Text("shrug".localized)
                            .padding(.bottom, AppMargins.small)
                        Text("empty_wallet".localized)
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
                                        Text("delete".localized)
                                        Image(systemName: "trash")
                                    }
                                }
                        }
                    }
                    .listStyle(PlainListStyle())
                }
            }
        }
        .navigationTitle("my_wallet".localized)
        .navigationBarItems(/*leading: refreshButton,*/
            trailing: Button(
                action: {
                    self.showAddTransaction.toggle()
                }) {
                    Image(systemName: "plus")
                }
        )
        .sheet(isPresented: self.$showAddTransaction) {
            AddTransactionScreen(showSheet: self.$showAddTransaction)
        }
        .onAppear {
            self.onAppear()
        }
        .onChange(of: self.screenErrorData) { errorData in
            self.appErrorData = errorData
        }
        .onChange(of: self.homeModel) { model  in
            if model is HomeModel.Error {
                if let uiErrorMessage = (model as? HomeModel.Error)?.uiErrorMessage {
                    self.appErrorData = uiErrorMessage.toSnackbarData()
                }
            }
        }.onReceive(NotificationCenter.default.publisher(for: .databaseReloaded)) { _ in
            print("Reload received")
            // Start observing again without stopping, because the use case has been already restored
            onAppear()
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static let model = HomeModel.HomeState(
        balanceRecap: BalanceRecap(totalBalance: 100, monthlyIncome: 150, monthlyExpenses: 50),
        latestTransactions: [
            MoneyTransaction(
                id: 1,
                title: "Transaction",
                icon: CategoryIcon.icAddressBook,
                amount: 50,
                type: TransactionTypeUI.expense,
                milliseconds: 123456,
                formattedDate: "20/10/21"
            )
        ]
    )

    static var previews: some View {
        HomeScreenContent(
            appErrorData: .constant(SnackbarData.init()),
            screenErrorData: .constant(SnackbarData.init()),
            homeModel: .constant(model ) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )

        HomeScreenContent(
            appErrorData: .constant(SnackbarData.init()),
            screenErrorData: .constant(SnackbarData.init()),
            homeModel: .constant(HomeModel.Loading()) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )

        HomeScreenContent(
            appErrorData: .constant(
                SnackbarData(
                    title: "An error occoured",
                    subtitle: "Error code 1012",
                    showBanner: true
                )
            ),
            screenErrorData: .constant(SnackbarData.init()),
            homeModel: .constant(
                HomeModel.Error(
                    uiErrorMessage: UIErrorMessage(
                        message: "Error!",
                        nerdMessage: "Error code: 101"
                    )
                )
            ) ,
            onAppear: {},
            deleteTransaction: {_ in }
        )
    }
}
