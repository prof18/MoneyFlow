//
//  DIContainer.swift
//  iosApp (iOS)
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

    func getHomeUseCase() -> HomeUseCaseIos {
        guard let useCase = koin.getFromScope(objCClass: HomeUseCaseIos.self) as? HomeUseCaseIos else {
            fatalError("HomeUseCaseIos cannot be null")
        }
        return useCase

    }

    func getCategoriesUseCase() -> CategoriesUseCaseIos {
        guard let useCase = koin.getFromScope(
            objCClass: CategoriesUseCaseIos.self
        ) as? CategoriesUseCaseIos else {
            fatalError("CategoriesUseCaseIos cannot be null")
        }
        return useCase
    }

    func getAddTransactionUseCase() -> AddTransactionUseCaseIos {
        guard let useCase = koin.getFromScope(
            objCClass: AddTransactionUseCaseIos.self
        ) as? AddTransactionUseCaseIos else {
            fatalError("AddTransactionUseCaseIos cannot be null")
        }
        return useCase
    }

    func getErrorMapper() -> MoneyFlowErrorMapper {
        guard let mapper = koin.get(objCClass: MoneyFlowErrorMapper.self) as? MoneyFlowErrorMapper else {
            fatalError("MoneyFlowErrorMapper cannot be null")
        }
        return mapper
    }

    func getDropboxSyncUseCase() -> DropboxSyncUseCaseIos {
        guard let useCase = koin.get(
            objCClass: DropboxSyncUseCaseIos.self
        ) as? DropboxSyncUseCaseIos else {
            fatalError("DropboxSyncUseCaseIos cannot be null")
        }
        return useCase
    }

    func reloadDIGraph() {
        koin.closeScope()
        koin.openKoinScope()
    }
}
