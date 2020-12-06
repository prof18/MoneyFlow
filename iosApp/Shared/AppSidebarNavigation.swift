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
        VStack {
            List() {
                NavigationLink(destination: HomeScreen(), tag: NavigationItem.home, selection: $selection) {
                    Label("Home", systemImage: "house")
                        .font(Font.headline.weight(selection == .home ? .bold : .regular))

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
                
                Divider()
                NavigationLink(destination: SettingsScreen(), tag: NavigationItem.settings, selection: $selection) {
                               Label("Settings", systemImage: "gear")
                           }
                           .tag(NavigationItem.settings)
            }
            
         
        }
        
    }
    
    var body: some View {
        NavigationView {
            sidebar
            Text("aronne")
            Text("Agaeg")
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
