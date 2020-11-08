//
//  AppTabNavigation.swift
//  iosApp
//
//  Created by Marco Gomiero on 08/11/2020.
//

import SwiftUI

// MARK: - AppTabNavigation

struct AppTabNavigation: View {
    @State private var selection: Tab = .home
    
    var body: some View {
        TabView(selection: $selection) {
            NavigationView {
                HomeScreen()
            }
            .tabItem {
                Label("Home", systemImage: "house")
                    .accessibility(label: Text("Home"))
            }
            .tag(Tab.home)
            
            
            NavigationView {
                RecapScreen()
            }
            .tabItem {
                Label("Recap", systemImage: "chart.pie")
                    .accessibility(label: Text("Recap"))
            }
            .tag(Tab.recap)
            
            NavigationView {
                BudgetScreen()
            }
            .tabItem {
                Label("Budget", systemImage: "banknote")
                    .accessibility(label: Text("Budget"))
            }
            .tag(Tab.budget)
            
            NavigationView {
                SettingsScreen()
            }
            .tabItem {
                Label("Settings", systemImage: "gear")
                    .accessibility(label: Text("Settings"))
            }
            .tag(Tab.settings)
        }
    }
}

// MARK: - Tab

extension AppTabNavigation {
    enum Tab {
        case home
        case recap
        case budget
        case settings
    }
}

// MARK: - Previews

struct AppTabNavigation_Previews: PreviewProvider {
    static var previews: some View {
        AppTabNavigation()
    }
}


