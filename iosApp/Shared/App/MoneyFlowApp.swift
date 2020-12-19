//
//  iosAppApp.swift
//  Shared
//
//  Created by Marco Gomiero on 25/08/2020.
//

import SwiftUI
import shared
import SwiftyDropbox

@main
struct MoneyFlowApp: App {
    
    @Environment(\.scenePhase) var scenePhase
    @StateObject var appState: AppState = AppState()

    //    @StateObject var appState: AppState = AppState()
    
    init() {
        UINavigationBar.appearance().largeTitleTextAttributes = [
            .foregroundColor: UIColor(named: "ColorOnBackground")! as UIColor,
            .font : UIFont(name:"Poppins-Regular", size: 32)!]
        
        let dropboxKey = Bundle.main.infoDictionary?["DropboxApiKey"] as? String ?? ""
        
        
        #if os(iOS)
        DropboxClientsManager.setupWithAppKey(dropboxKey)
        #else
        DropboxClientsManager.setupWithAppKeyDesktop(dropboxKey)
        #endif
        
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(appState)
                .onOpenURL { (url) in
                    print(url)
                    let oauthCompletion: DropboxOAuthCompletion = {
                        if let authResult = $0 {
                            switch authResult {
                            case .success:
                                print("Success! User is logged into DropboxClientsManager.")
                                NotificationCenter.default.post(name: .didDropboxSuccess, object: nil)
                            case .cancel:
                                print("Authorization flow was manually canceled by user!")
                                NotificationCenter.default.post(name: .didDropboxCancel, object: nil)
                            case .error(_, let description):
                                print("Error: \(String(describing: description))")
                                NotificationCenter.default.post(name: .didDropboxError, object: nil)
                            }
                        }
                    }
                    DropboxClientsManager.handleRedirectURL(url, completion: oauthCompletion)
                }
        }
                .onChange(of: scenePhase) { newScenePhase in
                    switch newScenePhase {
                    case .active:
                        print("App is active")
                    case .inactive:
                        print("App is inactive")
                    case .background:
                        print("App is in background")
                    @unknown default:
                        print("Oh - interesting: I received an unexpected new value.")
                    }
                }
    }
}
