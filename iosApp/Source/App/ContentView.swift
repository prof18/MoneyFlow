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
    
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    
    var body: some View {
        if horizontalSizeClass == .compact {
            AppTabNavigation().environmentObject(appState)
        } else {
            AppSidebarNavigation().environmentObject(appState)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
