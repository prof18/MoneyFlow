//
//  DIContainer.swift
//  App ()
//
//  Created by Marco Gomiero on 05/11/2020.
//

import Foundation
import shared

func startKoin() {
    let koinApplication = KoinIosKt.doInitKoinIos(
        dropboxDataSource: DropboxDataSourceIOS()
    )
    _koin = koinApplication.koin
    koin.openKoinScope()
}

private var _koin: Koin_coreKoin?
var koin: Koin_coreKoin {
    return _koin!
}

// swiftlint:disable identifier_name
let DI = DepsInjector.instance
// swiftlint:enable identifier_name

class DepsInjector {

    /// Singleton
    fileprivate  static let instance = DepsInjector()

    private init() {
    }

    func getHomeUseCase() -> HomeUseCase {
        guard let useCase = koin.getFromScope(objCClass: HomeUseCase.self) as? HomeUseCase else {
            fatalError("HomeUseCase cannot be null")
        }
        return useCase

    }

    func getCategoriesUseCase() -> CategoriesUseCase {
        guard let useCase = koin.getFromScope(
            objCClass: CategoriesUseCase.self
        ) as? CategoriesUseCase else {
            fatalError("CategoriesUseCase cannot be null")
        }
        return useCase
    }

    func getAddTransactionUseCase() -> AddTransactionUseCase {
        guard let useCase = koin.getFromScope(
            objCClass: AddTransactionUseCase.self
        ) as? AddTransactionUseCase else {
            fatalError("AddTransactionUseCase cannot be null")
        }
        return useCase
    }

    func getErrorMapper() -> MoneyFlowErrorMapper {
        guard let mapper = koin.get(objCClass: MoneyFlowErrorMapper.self) as? MoneyFlowErrorMapper else {
            fatalError("MoneyFlowErrorMapper cannot be null")
        }
        return mapper
    }

    func getDropboxSyncUseCase() -> DropboxSyncUseCase {
        guard let useCase = koin.get(
            objCClass: DropboxSyncUseCase.self
        ) as? DropboxSyncUseCase else {
            fatalError("DropboxSyncUseCase cannot be null")
        }
        return useCase
    }

    func reloadDIGraph() {
        koin.closeScope()
        koin.openKoinScope()
    }
}
