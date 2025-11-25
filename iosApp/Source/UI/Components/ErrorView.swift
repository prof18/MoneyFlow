//
//  ErrorView.swift
//  MoneyFlow
//
//  Created by Marco Gomiero on 30.12.21.
//

import SwiftUI
import ComposeApp

struct ErrorView: View {

    let uiErrorMessage: UIErrorMessage

    var body: some View {
        VStack {
            Spacer()
            VStack {
                Text("shrug".localized)
                    .padding(.bottom, AppMargins.small)
                Text(uiErrorMessage.localizedMessage())
                    .font(AppFonts.body1)
                if !uiErrorMessage.localizedNerdMessage().isEmpty {
                    Text(uiErrorMessage.localizedNerdMessage())
                        .font(AppFonts.caption)
                }
            }
            Spacer()
        }
    }
}

struct ErrorView_Previews: PreviewProvider {

    static let error = UIErrorMessageFactoryKt.uiErrorMessageFromKeys(
        messageKey: "error_get_money_summary_message",
        nerdMessageKey: "error_nerd_message",
        nerdMessageArgs: ["101"]
    )

    static var previews: some View {
        ErrorView(uiErrorMessage: error)
    }
}
