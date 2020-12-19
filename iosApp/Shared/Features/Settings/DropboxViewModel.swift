//
//  DropboxScreenViewModel.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 17/12/2020.
//

import shared
import SwiftyDropbox

class DropboxViewModel: ObservableObject {
    
    let dropboxClientManager = DropboxClientManager.instance
    @Published var isDropboxConnected: Bool = false
    
    
    // Method to observe the client status
    func backup() {
        self.dropboxClientManager.backup()
    }
    
    func checkIfConnected() {
        self.isDropboxConnected = dropboxClientManager.isConnected()
    }
    
    func setupDropboxClientManager() {
        self.dropboxClientManager.setup()
        self.checkIfConnected()
    }

    func unlink() {
        self.dropboxClientManager.unlink()
        self.isDropboxConnected = false
    }
    
    func restore() {
        self.dropboxClientManager.restore()
    }
    
    
    
}
