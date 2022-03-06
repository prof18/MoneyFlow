//
//  AppState.swift
//  iosApp
//
//  Created by Marco Gomiero on 08/12/2020.
//

import Foundation

class AppState: ObservableObject {

    @Published var snackbarData: SnackbarData = SnackbarData()

    @Published var snackbarDataForSheet: SnackbarData = SnackbarData()
}
