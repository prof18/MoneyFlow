//
//  UIErrorDataMapper.swift
//  MoneyFlow
//
//  Created by Marco Gomiero on 29.12.21.
//

import shared

extension UIErrorMessage {
    func toUIErrorData() -> UIErrorData {
        return UIErrorData(title: self.message, nerdishDesc: self.nerdMessage, showBanner: true)
    }
}
