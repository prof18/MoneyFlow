//
//  DropboxClient.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 18/12/2020.
//

import Foundation
import SwiftyDropbox

class DropboxClientManager {
    
    /// Singleton
    static let instance = DropboxClientManager()
    
    // TODO: add a publisher to show a loader or an error? Or maybe add a completion
    
    var client: DropboxClient?
    
    private init() {
        client = DropboxClientsManager.authorizedClient
    }
    
    func setup() {
        client = DropboxClientsManager.authorizedClient
    }
    
    func unlink() {
        DropboxClientsManager.unlinkClients()
    }
    
    func isConnected() -> Bool {
        return DropboxClientsManager.authorizedClient != nil
    }
    
    func backup() {
        if let databaseUrl = DatabaseImportExport.getDatabaseURL() {
            
            do {
                let fileData = try Data(contentsOf: databaseUrl)
                let client = DropboxClientsManager.authorizedClient
                _ = client?.files.upload(path: "/MoneyFlowDB.db", input: fileData)
                    .response { response, error in
                        if let response = response {
                            print(response)
                        } else if let error = error {
                            print(error)
                        }
                    }
                    .progress { progressData in
                        print(progressData)
                    }
                
                
            } catch {
                print("Unable to load com.prof18.moneyflow.data: \(error)")
            }
            
        }
    }
    
    func getMetadata() {
        
        
        
    }
    
    func restore() {
        
        let fileManager = FileManager.default
        let directoryURL = fileManager.urls(for: .documentDirectory, in: .userDomainMask)[0]
        let destURL = directoryURL.appendingPathComponent("MoneyFlowDB.db")
        let destination: (URL, HTTPURLResponse) -> URL = { temporaryURL, response in
            return destURL
        }
        
        client?.files.download(path: "/MoneyFlowDB.db", overwrite: true, destination: destination)
            .response { response, error in
                if let response = response {
                    
                    let contentHash = response.0.contentHash
                    // Move the content hash to settings
                    // TODO: decide if refresh or not
                    
                    DatabaseImportExport.replaceDatabase(url: destURL)
                    DIContainer.instance.reloadDatabaseRef()
                    print(response)
                } else if let error = error {
                    print(error)
                }
            }
            .progress { progressData in
                print(progressData)
            }
    }
}
