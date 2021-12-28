//
//  UIErrorData.swift
//  MoneyFlow
//
//  Created by Marco Gomiero on 28.12.21.
//

import Foundation

struct UIErrorData: Equatable {
    let title: String
    let nerdishDesc: String
    var showBanner: Bool

    init() {
        self.title = ""
        self.nerdishDesc = ""
        self.showBanner = false
    }

    init(title: String, nerdishDesc: String, showBanner: Bool) {
        self.title = title
        self.nerdishDesc = nerdishDesc
        self.showBanner = showBanner
    }

    static func == (lhs: UIErrorData, rhs: UIErrorData) -> Bool {
        return lhs.nerdishDesc == rhs.nerdishDesc && lhs.title == rhs.title
    }

}

extension UIErrorData {
    func isEmpty() -> Bool {
        return self.title == "" && self.nerdishDesc == "" && self.showBanner == false
    }
}
