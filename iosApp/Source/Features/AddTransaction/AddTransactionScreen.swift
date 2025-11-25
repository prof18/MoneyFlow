//
//  AddTransactionScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import SwiftUI
import shared

struct AddTransactionScreen: View {

    @EnvironmentObject var appState: AppState
    @Binding var showSheet: Bool
    @StateObject var addTransactionState = AddTransactionState()
    @StateObject var viewModel = AddTransactionViewModel()

    var body: some View {
        AddTransactionScreenContent(
            showSheet: $showSheet,
            transactionTypeUI: $viewModel.transactionTypeUI,
            saveDisabled: $viewModel.saveDisabled,
            amountTextField: $viewModel.amountTextField,
            descriptionTextField: $viewModel.descriptionTextField,
            transactionDate: $viewModel.transactionDate,
            addTransactionAction: $viewModel.addTransactionAction,
            addTransactionState: addTransactionState,
            addTransaction: { viewModel.addTransaction() },
            setCategoryId: { id in viewModel.categoryId = id  },
            transactionTypes: viewModel.transactionsType
        )
    }
}

struct AddTransactionScreenContent: View {

    @EnvironmentObject var appState: AppState
    @Binding var showSheet: Bool
    @Binding var transactionTypeUI: TransactionTypeUI
    @Binding var saveDisabled: Bool
    @Binding var amountTextField: String
    @Binding var descriptionTextField: String
    @Binding var transactionDate: Date
    @Binding var addTransactionAction: AddTransactionAction?

    @StateObject var addTransactionState: AddTransactionState

    let addTransaction : () -> Void
    let setCategoryId: (Int64?) -> Void

    let transactionTypes: [TransactionTypeRadioItem]

    @State private var showsDatePicker = false

    let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        return formatter
    }()

    var body: some View {
        NavigationView {
            ZStack {
                VStack {

                    Form {
                        Picker("transaction_type".localized, selection: $transactionTypeUI) {
                            ForEach(transactionTypes) {
                                Text($0.name).tag($0 as TransactionTypeRadioItem)
                            }
                        }

                        HStack {
                            DMImage(imageName: "ic_euro_sign", color: Color.colorOnBackground)
                            TextField("0.00", text: $amountTextField)
                                .keyboardType(.decimalPad)
                        }

                        HStack {
                            DMImage(imageName: "ic_edit", color: Color.colorOnBackground)
                            TextField("description".localized, text: $descriptionTextField)
                        }

                        NavigationLink(destination: CategoriesScreen(
                            addTransactionState: addTransactionState
                        )) {
                            HStack {
                                DMImage(
                                    imageName: addTransactionState.categoryIcon ?? "ic_question_circle",
                                    color: Color.colorOnBackground
                                )
                                Text(addTransactionState.categoryTitle ?? "select_category".localized)
                            }
                        }

                        HStack {
                            DMImage(imageName: "ic_calendar", color: Color.colorOnBackground)
                            Text("\(dateFormatter.string(from: transactionDate))")
                        }
                        .frame(
                            minWidth: 0,
                            maxWidth: .infinity,
                            minHeight: 20,
                            maxHeight: 20,
                            alignment: .leading
                        )
                        .contentShape(Rectangle())
                        .onTapGesture {
                            self.showsDatePicker.toggle()
                        }

                        if showsDatePicker {
                            DatePicker("", selection: $transactionDate, displayedComponents: .date)
                                .datePickerStyle(GraphicalDatePickerStyle())
                                .padding(20)
                        }
                    }
                }

                VStack(spacing: 0) {

                    Spacer()

                    Snackbar(snackbarData: $appState.snackbarDataForSheet)
                }
            }
            .navigationTitle(Text("add_transaction_screen".localized))
            .navigationBarItems(leading: Button(action: {
                self.showSheet.toggle()
            }) {
                Text("close".localized)

            }, trailing: Button(
                action: {
                    // TODO: do not close here but check the error before!!
                    self.addTransaction()
                }) {
                    Text("save".localized)
                }.disabled(saveDisabled)
            )
            .onReceive(addTransactionState.$categoryId) { value in
                setCategoryId(value)
            }
            .onChange(of: addTransactionAction) { action in
                if let errorAction = action as? AddTransactionAction.ShowError {
                    self.appState.snackbarData = errorAction.uiErrorMessage.toSnackbarData()
                } else if action is AddTransactionAction.GoBack {
                    self.showSheet.toggle()
                }
            }
        }

    }
}

struct AddTransactionScreen_Previews: PreviewProvider {

    static let transactionsType: [TransactionTypeRadioItem] = [
        TransactionTypeRadioItem(name: "Expense", id: .expense),
        TransactionTypeRadioItem(name: "Income", id: .income)
    ]
    static let addTransactionEmptyState =  AddTransactionState()
    static let appErrorData = SnackbarData(
        title: "An error occoured",
        subtitle: "Error code 1012",
        showBanner: true
    )
    static let addTransactionState = AddTransactionState(
        categoryId: 1,
        categoryTitle: "Category Name",
        categoryIcon: CategoryIcon.icAddressBook.iconName
    )

    static var previews: some View {
        AddTransactionScreenContent(
            showSheet: .constant(false ),
            transactionTypeUI: .constant(TransactionTypeUI.expense),
            saveDisabled: .constant(false ) ,
            amountTextField: .constant(""),
            descriptionTextField: .constant(""),
            transactionDate: .constant(Date()),
            addTransactionAction: .constant(nil),
            addTransactionState: addTransactionEmptyState,
            addTransaction: {},
            setCategoryId: { _ in  },
            transactionTypes: transactionsType
        )

        AddTransactionScreenContent(
            showSheet: .constant(false ),
            transactionTypeUI: .constant(TransactionTypeUI.income ),
            saveDisabled: .constant(false ) ,
            amountTextField: .constant("20"),
            descriptionTextField: .constant("A Description"),
            transactionDate: .constant(Date()),
            addTransactionAction: .constant(nil),
            addTransactionState: addTransactionState,
            addTransaction: {},
            setCategoryId: { _ in  },
            transactionTypes: transactionsType
        )
    }
}
