//
//  AppSidebarNavigation.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 08/11/2020.
//

import SwiftUI

struct AppSidebarNavigation: View {

    @State private var selection: SidebarNavigationItem? = .home
    @State var showSettingsSheet: Bool = false
    @EnvironmentObject var appState: AppState

    var sidebar: some View {

        VStack {

            NavigationLink(
                destination: HomeScreen().environmentObject(appState),
                tag: SidebarNavigationItem.home,
                selection: $selection,
                label: {
                    SidebarMenuItem(currentSelection: $selection,
                                    imageName: "house",
                                    textString: "Home",
                                    itemSelection: .home)
                })
                .buttonStyle(PlainButtonStyle())

            NavigationLink(
                destination: RecapScreen().environmentObject(appState),
                tag: SidebarNavigationItem.recap,
                selection: $selection,
                label: {
                    SidebarMenuItem(currentSelection: $selection,
                                    imageName: "chart.pie",
                                    textString: "Recap",
                                    itemSelection: .recap)
                })
                .buttonStyle(PlainButtonStyle())

            NavigationLink(
                destination: BudgetScreen().environmentObject(appState),
                tag: SidebarNavigationItem.budget,
                selection: $selection,
                label: {
                    SidebarMenuItem(currentSelection: $selection,
                                    imageName: "banknote",
                                    textString: "Budget",
                                    itemSelection: .budget)
                })
                .buttonStyle(PlainButtonStyle())

            Spacer()

            Divider()
            SidebarMenuItem(
                currentSelection: $selection,
                imageName: "gear",
                textString: "Settings",
                itemSelection: .settings
            ).onTapGesture {
                self.showSettingsSheet.toggle()
            }
        }
        .padding(.top, AppMargins.regular)
        .sheet(isPresented: self.$showSettingsSheet) {
            SettingsScreen().environmentObject(appState)
        }
        .navigationBarTitle(
            Text("Money Flow")

        )
    }

    var body: some View {
        NavigationView {
            sidebar
        }
    }
}
