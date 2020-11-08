//
//  AppSidebarNavigation.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 08/11/2020.
//

import SwiftUI

struct AppSidebarNavigation: View {

    enum NavigationItem {
        case home
        case recap
        case budget
        case settings
    }

    @State private var selection: NavigationItem? = .home
    
    var sidebar: some View {
        List(selection: $selection) {
            NavigationLink(destination: HomeScreen(), tag: NavigationItem.home, selection: $selection) {
                Label("Home", systemImage: "house")
            }
            .tag(NavigationItem.home)
            
            NavigationLink(destination: RecapScreen(), tag: NavigationItem.recap, selection: $selection) {
                Label("Recap", systemImage: "chart.pie")
            }
            .tag(NavigationItem.recap)
        
            NavigationLink(destination: BudgetScreen(), tag: NavigationItem.budget, selection: $selection) {
                Label("Budget", systemImage: "banknote")
            }
            .tag(NavigationItem.budget)
            
            NavigationLink(destination: SettingsScreen(), tag: NavigationItem.settings, selection: $selection) {
                Label("Settings", systemImage: "gear")
            }
            .tag(NavigationItem.settings)
        }
        .listStyle(SidebarListStyle())
    }
    
    var body: some View {
        NavigationView {
            sidebar
            
        }
    }
}

//struct AppSidebarNavigation_Previews: PreviewProvider {
//    static var previews: some View {
//        AppSidebarNavigation()
//            .environmentObject(FrutaModel())
//    }
//}
//
//struct AppSidebarNavigation_Pocket_Previews: PreviewProvider {
//    static var previews: some View {
//        AppSidebarNavigation.Pocket(presentingRewards: .constant(false))
//            .environmentObject(FrutaModel())
//            .frame(width: 300)
//    }
//}
