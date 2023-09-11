//
//  AddTransactionViewModel.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 06/12/2020.
//

import shared

class AddTransactionViewModel: ObservableObject {

    @Published var amountTextField : String = "" {
        didSet {
            updateSaveButtonStatus()
        }
    }
    @Published var descriptionTextField : String = ""
    @Published var transactionTypeUI: TransactionTypeUI = .expense
    @Published var transactionDate = Date()
    @Published var saveDisabled: Bool = true
    var categoryId: Int64? {
        didSet {
            updateSaveButtonStatus()
        }
    }
    @Published var addTransactionAction: AddTransactionAction?

    let transactionsType: [TransactionTypeRadioItem] = [
        TransactionTypeRadioItem(name: "Expense", id: .expense),
        TransactionTypeRadioItem(name: "Income", id: .income)
    ]

    private func updateSaveButtonStatus() {
        let canSave = !amountTextField.isEmpty && categoryId != nil
        self.saveDisabled = !canSave
    }

    private var addTransactionUseCase: AddTransactionUseCase = DI .getAddTransactionUseCase()

    func addTransaction() {

        let amount = Double(amountTextField)

        guard amount != nil else {
            let uiErrorMessage = UIErrorMessage(
                message: "amount_not_empty_error".localized,
                nerdMessage: ""
            )
            self.addTransactionAction = AddTransactionAction.ShowError(uiErrorMessage: uiErrorMessage)
            return
        }

        var transactionType = TransactionType.income
        if transactionTypeUI == TransactionTypeUI.expense {
            transactionType = .outcome
        }

        addTransactionUseCase.insertTransaction(
            transactionToSave: TransactionToSave(
                dateMillis: transactionDate.millisecondsSinceEpoch(),
                amount: amount!,
                description: descriptionTextField,
                categoryId: categoryId!,
                transactionType: transactionType
            ),
            onSuccess: {
                onMainThread {
                    self.addTransactionAction = AddTransactionAction.GoBack()
                }
            },
            onError: { error in
                onMainThread {
                    self.addTransactionAction = AddTransactionAction.ShowError(uiErrorMessage: error)
                }
            }
        )
    }

    func resetAction() {
        self.addTransactionAction = nil
    }

    deinit {
        addTransactionUseCase.onDestroy()
    }
}
