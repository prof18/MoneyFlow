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
    @Published var transactionTypeUI: TransactionTypeUI = .income
    @Published var transactionDate = Date()
    @Published var saveDisabled: Bool = true
    var categoryId: Int64? = nil {
        didSet {
            updateSaveButtonStatus()
        }
    }
    
    let transactions: [TransactionTypeRadioItem] = [
        TransactionTypeRadioItem(name: "Income", id: .income),
        TransactionTypeRadioItem(name: "Expense", id: .expense)
    ]
    
    let addTransactionUseCase: AddTransactionUseCase
    
    init() {
        addTransactionUseCase = AddTransactionUseCaseImpl(moneyRepository: DIContainer.instance.getMoneyRepository(), onInsertDone: {
            
            // TODO: save to dropbox
            
        })
    }
    
    private func updateSaveButtonStatus() {
        let canSave = !amountTextField.isEmpty && categoryId != nil
        self.saveDisabled = !canSave
    }
    
    func addTransaction() {
        
        let amount = Double(amountTextField)
        
        guard amount != nil else {
            // TODO: show error
            return
        }
        
        var transactionType = TransactionType.income
        if transactionTypeUI == TransactionTypeUI.expense {
            transactionType = .outcome
        }
        
        do {
            try addTransactionUseCase.insertTransaction(transactionToSave: TransactionToSave(
                dateMillis: transactionDate.millisecondsSinceEpoch(),
                amount: amount!,
                description: descriptionTextField,
                categoryId: categoryId!,
                transactionType: transactionType
            ))
        } catch let error  {
            print(error.localizedDescription)
        }
        
        
    }
}
