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
    var categoryId: Int64? = nil {
        didSet {
            updateSaveButtonStatus()
        }
    }
    @Published var uiErrorData: UIErrorData = UIErrorData()
    
    let transactionsType: [TransactionTypeRadioItem] = [
        TransactionTypeRadioItem(name: "Expense", id: .expense),
        TransactionTypeRadioItem(name: "Income", id: .income)
    ]
    
        
    private func updateSaveButtonStatus() {
        let canSave = !amountTextField.isEmpty && categoryId != nil
        self.saveDisabled = !canSave
    }
    
    private var addTransactionUseCase: AddTransactionUseCaseIos = DI .getAddTransactionUseCase()
    
    func addTransaction() {
        
        let amount = Double(amountTextField)
        
        guard amount != nil else {
            self.uiErrorData = UIErrorData(title: "amount_not_empty_error".localized, nerdishDesc: "", showBanner: true)
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
            onError: { error in
                self.uiErrorData = error.toUIErrorData()
            }
        )
    }
    
    deinit {
        addTransactionUseCase.onDestroy()
    }
}
