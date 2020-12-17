//
//  DropboxScreenViewModel.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 17/12/2020.
//

import shared
import SwiftyDropbox

class DropboxViewModel: ObservableObject {
    
    @Published var isDropboxConnected: Bool = false
    
    
    // Method to observe the client status
    
    func checkIfConnected() {
        self.isDropboxConnected = DropboxClientsManager.authorizedClient != nil
    }

    func unlink() {
        DropboxClientsManager.resetClients()
        self.isDropboxConnected = false
    }
    
    
}
