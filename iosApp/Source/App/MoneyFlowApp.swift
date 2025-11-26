//
//  iosAppApp.swift
//  Shared
//
//  Created by Marco Gomiero on 25/08/2020.
//

import SwiftUI
import shared

@main
struct MoneyFlowApp: App {
    init() {
        startKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
