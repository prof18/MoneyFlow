//
//  DropboxScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 17/12/2020.
//

import SwiftUI
import shared

struct DropboxScreen: View {

    @EnvironmentObject var appState: AppState
    @StateObject var dropboxViewModel: DropboxViewModel = DropboxViewModel()

    var body: some View {
        DropboxScreenContent(
            isDropboxConnected: $dropboxViewModel.isDropboxConnected,
            appErrorData: $appState.errorData,
            metadataModel: $dropboxViewModel.metadataModel,
            screenErrorData: $dropboxViewModel.uiErrorData,
            onAppear: {
                dropboxViewModel.restoreClient()
            },
            onDropboxAuthSuccess: {
                dropboxViewModel.saveDropboxAuth()
            },
            backupToDropbox: {
                dropboxViewModel.backup()
            },
            restoreFromDropbox: {
                dropboxViewModel.restore()
            },
            unlinkDropbox: {
                dropboxViewModel.unlink()
            }
        )
    }

}

struct DropboxScreenContent: View {

    @Binding var isDropboxConnected: Bool
    @Binding var appErrorData: UIErrorData
    @Binding var metadataModel: DropboxSyncMetadataModel
    @Binding var screenErrorData: UIErrorData

    let onAppear: () -> Void
    let onDropboxAuthSuccess: () -> Void
    let backupToDropbox: () -> Void
    let restoreFromDropbox: () -> Void
    let unlinkDropbox: () -> Void

    @State private var showDropboxConnectScreen = false

    var body: some View {
        content
            .navigationTitle(Text("dropbox_sync".localized))
            .onReceive(NotificationCenter.default.publisher(for: .didDropboxSuccess)) { _ in
                print("Dropbox Success Notification")
                onDropboxAuthSuccess()
            }
            .onReceive(NotificationCenter.default.publisher(for: .didDropboxCancel)) { _ in
                print("Dropbox Cancel Notification")
            }
            .onReceive(NotificationCenter.default.publisher(for: .didDropboxError)) { _ in
                print("Dropbox Error Notification")
            }.onAppear {
                onAppear()
            }.onChange(of: self.screenErrorData) { errorData in
                self.appErrorData = errorData
            }
    }

    var content: AnyView {
        if isDropboxConnected {

            return AnyView(
                VStack(alignment: .leading) {

                    DropboxSyncMetadataUI(metadataModel: $metadataModel)
                        .padding(.horizontal, AppMargins.regular)

                    Form {

                        Button("backup_to_dropbox".localized) {
                            backupToDropbox()
                        }

                        Button("restore_from_dropbox".localized) {
                            restoreFromDropbox()
                        }

                        Button("unlink_dropbox".localized) {
                            unlinkDropbox()
                        }
                    }
                }
            )
        } else {
            return AnyView(
                VStack(alignment: .leading) {
                    Text("Not Connected")
                    Button("connect_dropbox".localized) {
                        self.showDropboxConnectScreen.toggle()
                    }
                    DropboxLoginView(isShown: self.$showDropboxConnectScreen)
                }.padding(AppMargins.regular)
            )
        }
    }
}

struct DropboxSyncMetadataUI: View {

    @Binding var metadataModel: DropboxSyncMetadataModel

    var body : some View {

        if (metadataModel is DropboxSyncMetadataModel.Loading) {
            HStack {
                Spacer()
                Loader()
                Spacer()
            }
        } else if let error = metadataModel as? DropboxSyncMetadataModel.Error {
            HStack {
                Spacer()
                ErrorView(uiErrorMessage: error.errorMessage)
                Spacer()
            }
        } else if let metadataModel = metadataModel as? DropboxSyncMetadataModel.Success {

            VStack(alignment: .leading) {
                Text("dropbox_sync_time".localized)
                    .font(AppFonts.body1)
                    .bold()
                    .padding(.top, AppMargins.small)

                Text(metadataModel.latestUploadFormattedDate)
                    .font(AppFonts.caption)
                    .padding(.top, AppMargins.xSmall)

                Text(metadataModel.latestDownloadFormattedDate)
                    .font(AppFonts.caption)

                Text("dropbox_nerd_stats".localized)
                    .font(AppFonts.body1)
                    .bold()
                    .padding(.top, AppMargins.regular)

                if let tldr = metadataModel.tlDrHashMessage {
                    Text(tldr)
                        .font(AppFonts.caption )
                        .padding(.top, AppMargins.xSmall)
                }

                Text(metadataModel.latestUploadHash)
                    .font(AppFonts.caption )
                    .padding(.top, AppMargins.xSmall)

                Text(metadataModel.latestDownloadHash)
                    .font(AppFonts.caption )
                    .padding(.bottom, AppMargins.small)
            }
        }
    }
}

struct DropboxScreen_Previews: PreviewProvider {

    static let model = DropboxSyncMetadataModel.Success(
        latestUploadFormattedDate: "Last upload: 5/3/2022 - 10:45:45",
        latestDownloadFormattedDate: "Last download: 5/3/2022 - 10:45:45",
        latestUploadHash: "c170b5a993943d9a89a6af569d9f4f723e52d2ca4c8d52400be0c714a294ba89",
        latestDownloadHash: "c170b5a993943d9a89a6af569d9f4f723e52d2ca4c8d52400be0c714a294ba89",
        tlDrHashMessage: "tl;dr content hash are the same"
    )

    static let errorModel = DropboxSyncMetadataModel.Error(
        errorMessage: UIErrorMessage(
            message: "Something bad happened",
            nerdMessage: "Nerd Error message"
        )
    )

    static var previews: some View {
        // With Success metadata
        DropboxScreenContent(
            isDropboxConnected: .constant(true ),
            appErrorData: .constant(UIErrorData.init()),
            metadataModel: .constant(model ) ,
            screenErrorData: .constant(UIErrorData.init()),
            onAppear: {},
            onDropboxAuthSuccess: {},
            backupToDropbox: {},
            restoreFromDropbox: {},
            unlinkDropbox: {}
        )

        // With Loading metadata
        DropboxScreenContent(
            isDropboxConnected: .constant(true ),
            appErrorData: .constant(UIErrorData.init()),
            metadataModel: .constant(DropboxSyncMetadataModel.Loading()),
            screenErrorData: .constant(UIErrorData.init()),
            onAppear: {},
            onDropboxAuthSuccess: {},
            backupToDropbox: {},
            restoreFromDropbox: {},
            unlinkDropbox: {}
        )

        // With Error metadata
        DropboxScreenContent(
            isDropboxConnected: .constant(true ),
            appErrorData: .constant(UIErrorData.init()),
            metadataModel: .constant(errorModel),
            screenErrorData: .constant(UIErrorData.init()),
            onAppear: {},
            onDropboxAuthSuccess: {},
            backupToDropbox: {},
            restoreFromDropbox: {},
            unlinkDropbox: {}
        )
    }
}
