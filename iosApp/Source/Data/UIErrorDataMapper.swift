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
            title: self.localizedMessage(),
            subtitle: self.localizedNerdMessage(),
            showBanner: true
        )
    }
}
