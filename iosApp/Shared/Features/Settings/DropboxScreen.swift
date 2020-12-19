//
//  DropboxScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 17/12/2020.
//

import SwiftUI

struct DropboxScreen: View {
    
    @State var showDropboxConnectScreen = false
    @StateObject var dropboxViewModel: DropboxViewModel = DropboxViewModel()
    
    var body: some View {
        content
            .navigationTitle(Text("Dropbox Sync"))
            .onReceive(NotificationCenter.default.publisher(for: .didDropboxSuccess)) { _ in
                print("Dropbox Success Notification")
                self.dropboxViewModel.setupDropboxClientManager()
            }
            .onReceive(NotificationCenter.default.publisher(for: .didDropboxCancel)) { _ in
                print("Dropbox Cancel Notification")
            }
            .onReceive(NotificationCenter.default.publisher(for: .didDropboxError)) { _ in
                print("Dropbox Error Notification")
            }.onAppear {
                self.dropboxViewModel.checkIfConnected()
            }
    }
    
    var content: AnyView {
        if self.dropboxViewModel.isDropboxConnected {
            return AnyView(
                
                Form {
                    
                    if self.dropboxViewModel.isDropboxConnected {
                        
                        Text("Connected")
                        
                        Button("Backup to Dropbox") {
                            dropboxViewModel.backup()
                        }
                        
                        Button("Restore from Dropbox") {
                            dropboxViewModel.restore()
                        }
                        
                        Button("Unlink") {
                            self.dropboxViewModel.unlink()
                        }
                        
                    }
                }
            )
        } else {
            return AnyView(
                VStack(alignment: .leading) {
                    Text("Not Connected")
                    Button("Link") {
                        self.showDropboxConnectScreen.toggle()
                    }
                    DropboxLoginView(isShown: self.$showDropboxConnectScreen)
                }.padding(AppMargins.regular)
            )
        }
    }
}

struct DropboxScreen_Previews: PreviewProvider {
    static var previews: some View {
        DropboxScreen()
    }
}
