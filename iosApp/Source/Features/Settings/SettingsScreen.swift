//
//  SettingsScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 08/11/2020.
//

import SwiftUI
import shared

struct SettingsScreen: View {

    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var appState: AppState
    @State var sheetToShow: SheetToShow?

    @Environment(\.horizontalSizeClass) private var horizontalSizeClass

    var body: some View {
        ZStack {
            if showNavigation() {
                NavigationView {
                    getContent()
                }
            } else {
                getContent()
            }

            VStack(spacing: 0) {

                Spacer()

                Snackbar(snackbarData: $appState.snackbarDataForSheet)
            }
        }
    }

    private func showNavigation() -> Bool {
        if horizontalSizeClass == .compact {
            return false
        } else {
            return true
        }
    }

    private func getContent() -> some View {
        return Form {
            Button("export_database".localized) {
                DatabaseHelper().dbClear()
                sheetToShow = .shareSheet
            }

            Button("import_database".localized) {
                sheetToShow = .filePicker
            }

            NavigationLink(destination: DropboxScreen()) {
                Button("dropbox_sync".localized) {}
            }

        }
        .navigationTitle(Text("settings_screen".localized))

        .navigationBarItems(leading: showNavigation() ? AnyView(closeButton) : AnyView(EmptyView()))
        .sheet(item: $sheetToShow, onDismiss: {
            // TODO: reload here the database?
            DI.reloadDIGraph()
            NotificationCenter.default.post(name: .databaseReloaded, object: nil)
        }) { item in

            switch item {

            case .filePicker:
                FilePickerController { url in
                    DatabaseImportExport.replaceDatabase(url: url)
                }

            case .shareSheet:
                ShareSheet(
                    activityItems: [DatabaseImportExport.getDatabaseURL() ?? ""] as [Any],
                    applicationActivities: nil
                ) { _,_,_,_ in
                    DI.reloadDIGraph()
                    NotificationCenter.default.post(name: .databaseReloaded, object: nil)
                }
            }
        }
    }

    var closeButton: some View {
        Button(action: {
            self.presentationMode.wrappedValue.dismiss()
        }) {
            Text("close".localized)
        }
    }
}

struct SettingsScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingsScreen()
    }
}
