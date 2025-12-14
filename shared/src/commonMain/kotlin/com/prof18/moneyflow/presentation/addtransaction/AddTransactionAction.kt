package com.prof18.moneyflow.presentation.addtransaction

import com.prof18.moneyflow.presentation.model.UIErrorMessage

internal sealed class AddTransactionAction {
    class ShowError(val uiErrorMessage: UIErrorMessage) : AddTransactionAction()
    object GoBack : AddTransactionAction()
}
