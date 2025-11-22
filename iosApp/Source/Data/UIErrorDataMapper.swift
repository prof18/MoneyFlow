//
//  UIErrorDataMapper.swift
//  MoneyFlow
//
//  Created by Marco Gomiero on 29.12.21.
//

import ComposeApp

extension UIErrorMessage {
    func toSnackbarData() -> SnackbarData {
        return SnackbarData(
            title: self.message,
            subtitle: self.nerdMessage,
            showBanner: true
        )
    }
}
