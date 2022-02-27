//
//  AppState.swift
//  iosApp
//
//  Created by Marco Gomiero on 08/12/2020.
//

import Foundation

class AppState: ObservableObject {

    @Published var reloadDatabase: Bool = false

    @Published var errorData: UIErrorData = UIErrorData()
}
