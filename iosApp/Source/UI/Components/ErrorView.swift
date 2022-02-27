//
//  ErrorView.swift
//  MoneyFlow
//
//  Created by Marco Gomiero on 30.12.21.
//

import SwiftUI
import shared

struct ErrorView: View {

    let uiErrorMessage: UIErrorMessage

    var body: some View {
        VStack {
            Spacer()
            VStack {
                Text("shrug".localized)
                    .padding(.bottom, AppMargins.small)
                Text(uiErrorMessage.message )
                    .font(AppFonts.body1)
                Text(uiErrorMessage.nerdMessage)
                    .font(AppFonts.caption)
            }
            Spacer()
        }
    }
}

struct ErrorView_Previews: PreviewProvider {

    static let error = UIErrorMessage(
        message: "An error occourred!",
        nerdMessage: "Error code 101"
    )

    static var previews: some View {
        ErrorView(uiErrorMessage: error)
    }
}
