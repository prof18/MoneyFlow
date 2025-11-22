//
//  DIContainer.swift
//  App ()
//
//  Created by Marco Gomiero on 05/11/2020.
//

import Foundation
import shared

func startKoin() {
    KoinIosDependencies.shared.start()
}

// swiftlint:disable identifier_name
let DI = DepsInjector.instance
// swiftlint:enable identifier_name

class DepsInjector {

    /// Singleton
    fileprivate  static let instance = DepsInjector()

    private init() {
    }

    func getHomeUseCase() -> HomeUseCase { KoinIosDependencies.shared.homeUseCase() }

    func getCategoriesUseCase() -> CategoriesUseCase { KoinIosDependencies.shared.categoriesUseCase() }

    func getAddTransactionUseCase() -> AddTransactionUseCase { KoinIosDependencies.shared.addTransactionUseCase() }

    func getErrorMapper() -> MoneyFlowErrorMapper { KoinIosDependencies.shared.errorMapper() }

    func reloadDIGraph() { KoinIosDependencies.shared.reload() }
}
