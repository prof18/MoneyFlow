//
//  DIContainer.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 05/11/2020.
//

import Foundation
import shared

class DIContainer {
    
    /// Singleton
    static let instance = DIContainer()
    private init() {}
    
    private var databaseSource: DatabaseSource?
    private var moneyRepository: MoneyRepositoryImpl?
    private var homeUseCaseIos: HomeUseCaseIos?
    private var categoryUseCaseIos: CategoriesUseCaseIos?
    private var addTransactionUseCaseIos: AddTransactionUseCaseIos?
    
    func getDatabaseSource() -> DatabaseSource {
        if self.databaseSource == nil {
            DatabaseHelper().setupDatabase(driver: nil)
            databaseSource = DatabaseSourceImpl(dbRef: DatabaseHelper().instance, dispatcher: nil)
        }
        return self.databaseSource!
    }
    
    func reloadDatabaseRef() {
        DatabaseHelper().setupDatabase(driver: nil)
        (databaseSource as! DatabaseSourceImpl).dbRef = DatabaseHelper().instance
        self.moneyRepository = nil 
    }
    
    func getMoneyRepository() -> MoneyRepository {
        if self.moneyRepository == nil {
            moneyRepository = MoneyRepositoryImpl(dbSource: getDatabaseSource())
        }
        return self.moneyRepository!
    }
    
    func getHomeUseCase() -> HomeUseCaseIos {
        if self.homeUseCaseIos == nil {
            let homeUseCase = HomeUseCase(
                moneyRepository: getMoneyRepository(),
                settingsRepository: koin.getSettingRepository()
            )
            self.homeUseCaseIos = HomeUseCaseIos(homeUseCase: homeUseCase)
        }
        return self.homeUseCaseIos!
    }
    
    func getCategoriesUseCase() -> CategoriesUseCaseIos {
        if self.categoryUseCaseIos == nil {
            let categoryUseCase = CategoriesUseCase(moneyRepository: getMoneyRepository())
            self.categoryUseCaseIos = CategoriesUseCaseIos(categoriesUseCase: categoryUseCase)
        }
        return self.categoryUseCaseIos!
    }
    
    func getAddTransactionUseCase() -> AddTransactionUseCaseIos {
        if self.addTransactionUseCaseIos == nil {
            let addTransactionUseCase = AddTransactionUseCase(moneyRepository: getMoneyRepository())
            self.addTransactionUseCaseIos = AddTransactionUseCaseIos(addTransactionUseCase: addTransactionUseCase)
        }
        return self.addTransactionUseCaseIos!
    }
    
}
