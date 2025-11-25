//
//  iosAppApp.swift
//  Shared
//
//  Created by Marco Gomiero on 25/08/2020.
//

import SwiftUI
import shared

@main
struct MoneyFlowApp: App {

    @Environment(\.scenePhase) var scenePhase
    @StateObject var appState: AppState = AppState()

    init() {

        startKoin()

        UINavigationBar.appearance().largeTitleTextAttributes = [
            .foregroundColor: UIColor(named: "ColorOnBackground")! as UIColor,
            .font: UIFont(name: "Poppins-Regular", size: 32)!]

        var key = ""
        if let path = Bundle.main.path(forResource: "Info", ofType: "plist") {
            if let keys = NSDictionary(contentsOfFile: path) {
                key = keys["DropboxApiKey"] as? String ?? ""
            }
        }
        DI.getDropboxSyncUseCase().setupClient(apiKey: key)
    }

    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(appState)
                    .onOpenURL { (url) in
                        print(url)

                        DI.getDropboxSyncUseCase().handleOAuthResponse {
                            DropboxDataSourceIOS.handleOAuthResponse(
                                    url: url,
                                    onSuccess: {
                                        print("Success! User is logged into DropboxClientsManager.")
                                        NotificationCenter.default.post(name: .didDropboxSuccess, object: nil)
                                    },
                                    onCancel: {
                                        print("Authorization flow was manually canceled by user!")
                                        NotificationCenter.default.post(name: .didDropboxCancel, object: nil)
                                    },
                                    onError: {
                                        print("Error")
                                        NotificationCenter.default.post(name: .didDropboxError, object: nil)
                                    }
                            )
                        }
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
