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
    
    var body: some View {
        NavigationView {
            Form {
                // TODO: localize
                Button("Export Database") {
                    DatabaseHelper().dbClear()
                    sheetToShow = .shareSheet
                }
                
                // TODO: localize
                Button("Import Database") {
                    sheetToShow = .filePicker
                }
                
            }
            .navigationTitle(Text("Settings"))
            .navigationBarItems(leading: Button(action: {
                self.presentationMode.wrappedValue.dismiss()
            }) {
                // TODO: localize
                Text("Close")
                
            })
            .sheet(item: $sheetToShow) { item in
                
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
    }
}

struct SettingsScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingsScreen()
    }
}
