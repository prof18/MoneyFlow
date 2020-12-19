//
//  ContentView.swift
//  Shared
//
//  Created by Marco Gomiero on 25/08/2020.
//

import SwiftUI
import shared

struct ContentView: View {
    
    @EnvironmentObject var appState: AppState
    
    #if os(iOS)
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    #endif
    
    var body: some View {
        #if os(iOS)
        if horizontalSizeClass == .compact {
            AppTabNavigation().environmentObject(appState)
        } else {
            AppSidebarNavigation().environmentObject(appState)
        }
        #else
        AppSidebarNavigation()
        #endif
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
