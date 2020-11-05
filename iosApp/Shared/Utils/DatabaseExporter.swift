//
//  DatabaseExporter.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 05/11/2020.
//

import Foundation


class DatabaseExporter {
    
    
//    // Just random stuff to be placed correctly
//    
//    func getDBURL() -> URL {
//        
//        let fileURL = try! FileManager.default
//            .url(for: .applicationSupportDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
//            .appendingPathComponent("databases/MoneyFlowDB")
//        
//        return fileURL
//        
//    }
//    
//    func replaceDB(url: URL) {
//        
//        var databaseSource = koin.get(objCClass: DatabaseSource.self, parameter: "HomeViewModel") as! DatabaseSource
////        databaseSource.close()
//        
//        let databaseHelper = DatabaseHelper()
//        
//        databaseHelper.dbClear()
//        
//
//        
//        
//        let fileURL = try! FileManager.default
//            .url(for: .applicationSupportDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
//            .appendingPathComponent("databases/MoneyFlowDB")
//        
//        let x = try! FileManager.default.replaceItemAt(fileURL, withItemAt: url, backupItemName: "MoneyFlowDB.old", options: .usingNewMetadataOnly)
//        
//        print("here")
//        
//
//        
//        databaseHelper.setupDatabase()
////        databaseSource.dbRef = databaseHelper.instance
////
////        let items = databaseSource.getData()
////
////        for item in items {
////            print(item.description())
////        }
//        
////        print("List is empty: \(items.isEmpty)")
//        
//        
//            self.useCase?.computeData()
//    }
//    
//    func reopenDb() {
//        
//        DIContainer.instance.reloadDatabaseRef()
//        
//        
//
//        useCase = HomeUseCaseImpl(moneyRepository: DIContainer.instance.getMoneyRepository(), viewUpdate: { [weak self] model in
//            self?.homeModel = model
//        })
//        
//        self.useCase?.computeData()
//
//    }
//    
//                .sheet(isPresented: $showingSheet,
//                         content: {
//                            ActivityView(activityItems: [viewModel.getDBURL()] as [Any], applicationActivities: nil) })
//                .sheet(isPresented: $showingFilePicker, content: { FilePickerController { url in
//                    print("Selected: \(url)")
//                    self.viewModel.replaceDB(url: url)
//                }})
//    
}
