//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Marco Gomiero on 05/09/2020.
//

import shared
import CoreData

class HomeViewModel: ObservableObject {
    
    @Published var homeModel: HomeModel = HomeModel.Loading()
    

    
    var useCase: HomeUseCaseImpl?
    
    lazy var applicationDocumentsDirectory: URL = {
            // The directory the application uses to store the Core Data store file. This code uses a directory named "com.appcoda.CoreDataDemo" in the application's documents Application Support directory.
        let urls = FileManager.init().urls(for: .documentDirectory, in: .userDomainMask)
            return urls[urls.count-1]
            }()
        
    
    func getDBURL() -> URL {
        
        let databaseSource = koin.get(objCClass: DatabaseSource.self, parameter: "HomeViewModel") as! DatabaseSource
//        databaseSource.close()
        

        
        let fileURL = try! FileManager.default
            .url(for: .applicationSupportDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
            .appendingPathComponent("databases/MoneyFlowDB")
        
        
//        koin.restartDatabaseScope()
        
        
//        let items = databaseSource.getData()
//    
//        
//        print("List is empty: \(items.isEmpty)")
        
        return fileURL
        

        
        
        
    }
    
    func replaceDB(url: URL) {
        
        var databaseSource = koin.get(objCClass: DatabaseSource.self, parameter: "HomeViewModel") as! DatabaseSource
//        databaseSource.close()
        
        let databaseHelper = DatabaseHelper()
        
        databaseHelper.dbClear()
        

        
        
        let fileURL = try! FileManager.default
            .url(for: .applicationSupportDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
            .appendingPathComponent("databases/MoneyFlowDB")
        
        let x = try! FileManager.default.replaceItemAt(fileURL, withItemAt: url, backupItemName: "MoneyFlowDB.old", options: .usingNewMetadataOnly)
        
        print("here")
        

        
        databaseHelper.setupDatabase()
//        databaseSource.dbRef = databaseHelper.instance
//
//        let items = databaseSource.getData()
//
//        for item in items {
//            print(item.description())
//        }
        
//        print("List is empty: \(items.isEmpty)")
        
        
        
//        print("List is empty: \(items.description)")
        
        print(" ")
        
        self.useCase?.computeData()
        
        
        
        
        
      
    }
    
    func startObserving() {
        
//        let databaseSource = koin.get(objCClass: DatabaseSource.self, parameter: "HomeViewModel") as! DatabaseSource
//
//        useCase = HomeUseCaseImpl(moneyRepository: MoneyRepositoryImpl(dbSource: databaseSource), viewUpdate: { [weak self] model in
//            self?.homeModel = model
//        })
        
    
//        let databaseSource = koin.getFromScope(objCClass: DatabaseSource.self, scopeID: "databaseScope") as! DatabaseSource

//        let items = databaseSource.getData()
//
//        for item in items {
//            print(item.description())
//        }
        
//        print("List is empty: \(items.isEmpty)")
        
        
        let fileURL = try! FileManager.default
            .url(for: .applicationSupportDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
            .appendingPathComponent("databases/MoneyFlowDB")
        
        print(fileURL)
        
        print(applicationDocumentsDirectory.path)

        
        self.useCase?.computeData()
    }
    
    func stopObserving() {
        self.useCase?.onDestroy()
    }
}
