//
//  DIContainer.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 05/11/2020.
//

import Foundation
import shared


func startKoin() {
    let koinApplication = KoinIosKt.doInitKoinIos()
    _koin = koinApplication.koin
    koin.openKoinScope()
}

private var _koin: Koin_coreKoin?
var koin: Koin_coreKoin {
    return _koin!
}

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
    
    func getErrorMapper() -> MoneyFlowErrorMapper {
        koin.get(objCClass: MoneyFlowErrorMapper.self) as! MoneyFlowErrorMapper
    }
    
    func reloadDIGraph() {
        koin.closeScope()
        koin.openKoinScope()
    }
}

