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
    
    #if os(iOS)
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    #endif
    
    var body: some View {
        if showNavigation() {
            NavigationView {
                getContent()
            }
        } else {
            getContent()
        }
    }
    
    private func showNavigation() -> Bool {
        #if os(iOS)
        if horizontalSizeClass == .compact {
            return false
        } else {
            return true
        }
        #else
        return true
        #endif
    }
    
    private func getContent() -> some View {
        return Form {
            // TODO: localize
            Button("Export Database") {
                DatabaseHelper().dbClear()
                sheetToShow = .shareSheet
            }
            
            // TODO: localize
            Button("Import Database") {
                sheetToShow = .filePicker
            }
            
            NavigationLink(destination: DropboxScreen()) {
                Button("Dropbox Sync") {}
            }
            
        }
        .navigationTitle(Text("Settings"))
        
        .navigationBarItems(leading: showNavigation() ? AnyView(closeButton) : AnyView(EmptyView()))
        .sheet(item: $sheetToShow, onDismiss: {
            // TODO: reload here the database
        }) { item in
            
            switch item {
            
            case .filePicker:
                FilePickerController { url in
                    DatabaseImportExport.replaceDatabase(url: url)
                    appState.reloadDatabase = true
                }
                
            case .shareSheet:
                
                ShareSheet(activityItems: [DatabaseImportExport.getDatabaseURL() ?? ""] as [Any], applicationActivities: nil) { _,_,_,_ in
                    
                    DIContainer.instance.reloadDatabaseRef()
                    appState.reloadDatabase = true
                }
            }
        }
    }
    
    var closeButton: some View {
        Button(action: {
            self.presentationMode.wrappedValue.dismiss()
        }) {
            // TODO: localize
            Text("Close")
            
        }
    }
}

struct SettingsScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingsScreen()
    }
}
