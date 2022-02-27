//
//  AddTransactionState.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import Foundation
import shared

class AddTransactionState: ObservableObject {
    @Published var categoryId: Int64?
    @Published var categoryTitle: String?
    @Published var categoryIcon: String?

    init() {

    }

    init(categoryId: Int64, categoryTitle: String, categoryIcon: String) {
        self.categoryId = categoryId
        self.categoryTitle = categoryTitle
        self.categoryIcon = categoryIcon
    }
}
