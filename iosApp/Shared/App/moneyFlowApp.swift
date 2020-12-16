//
//  iosAppApp.swift
//  Shared
//
//  Created by Marco Gomiero on 25/08/2020.
//

import SwiftUI
import shared

@main
struct moneyFlowApp: App {
    
    @Environment(\.scenePhase) var scenePhase
//    @StateObject var appState: AppState = AppState()
    
    init() {
        UINavigationBar.appearance().largeTitleTextAttributes = [
            .foregroundColor: UIColor(named: "ColorOnBackground")! as UIColor,
            .font : UIFont(name:"Poppins-Regular", size: 32)!]
        
        let x =  Bundle.main.infoDictionary?["SecretApi"] as? String ?? ""

        
        let path = Bundle.main.path(forResource: "Info", ofType: "plist")
        let url = URL(fileURLWithPath: path!)
        let data = try! Data(contentsOf: url)
        let plist = try! PropertyListSerialization.propertyList(from: data, options: .mutableContainers, format: nil) as? [[String:String]]
        print(plist)
        print("")
        
//
//        let url = Bundle.main.url(forResource: "Info", withExtension:"plist")!
//         do {
//             let data = try Data(contentsOf: url)
////             let result = try PropertyListDecoder().decode(Root.self, from: data)
////             self.animals = result.animals
//            print("")
//         } catch { print(error) }
        
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
//        .onChange(of: scenePhase) { newScenePhase in
//            switch newScenePhase {
//            case .active:
//                print("App is active")
//            case .inactive:
//                print("App is inactive")
//            case .background:
//                print("App is in background")
//            @unknown default:
//                print("Oh - interesting: I received an unexpected new value.")
//            }
//        }
    }
    
}
