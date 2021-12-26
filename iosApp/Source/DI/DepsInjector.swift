//
//  DIContainer.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 05/11/2020.
//

import Foundation
import shared

let DI = DepsInjector.instance

class DepsInjector {

    /// Singleton
    fileprivate  static let instance = DepsInjector()

    private init() {
    }
    
    func getHomeUseCase() -> HomeUseCaseIos {
        koin.getFromScope(objCClass: HomeUseCaseIos.self) as! HomeUseCaseIos
    }

    func getCategoriesUseCase() -> CategoriesUseCaseIos {
        koin.getFromScope(objCClass: CategoriesUseCaseIos.self) as! CategoriesUseCaseIos
    }

    func getAddTransactionUseCase() -> AddTransactionUseCaseIos {
        koin.getFromScope(objCClass: AddTransactionUseCaseIos.self) as! AddTransactionUseCaseIos
    }
    
    func reloadDIGraph() {
        koin.closeScope()
        koin.openKoinScope()
    }
}

