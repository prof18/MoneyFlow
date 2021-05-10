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
            homeUseCaseIos = HomeUseCaseIos(moneyRepository: DIContainer.instance.getMoneyRepository())
        }
        return self.homeUseCaseIos!
    }
    
}
