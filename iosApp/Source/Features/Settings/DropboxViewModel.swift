//
//  DropboxScreenViewModel.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 17/12/2020.
//

import shared
import Combine

class DropboxViewModel: ObservableObject {

    private var subscriptions = Set<AnyCancellable>()

    @Published var isDropboxConnected: Bool = false
    @Published var dropboxSyncAction: DropboxSyncAction?
    @Published var metadataModel: DropboxSyncMetadataModel = DropboxSyncMetadataModel.Loading()

    private func dropboxSyncUseCase() -> DropboxSyncUseCase {
        DI.getDropboxSyncUseCase()
    }

    func restoreClient() {

        createPublisher(dropboxSyncUseCase().observeDropboxSyncMetadataModel())
            .eraseToAnyPublisher()
            .receive(on: DispatchQueue.global(qos: .userInitiated))
            .sink(
                receiveCompletion: { completion in
                    if case let .failure(error) = completion {
                        let moneyFlowError = MoneyFlowError.DropboxMetadata(throwable:  error.throwable)
                        error.throwable.logError(
                            moneyFlowError: moneyFlowError,
                            message: "Got error while transforming Flow to Publisher"
                        )
                        let uiErrorMessage = DI.getErrorMapper().getUIErrorMessage(error: moneyFlowError)
                        self.metadataModel = DropboxSyncMetadataModel.Error(errorMessage: uiErrorMessage)
                    }
                },
                receiveValue: { genericResponse in
                    onMainThread {
                        self.metadataModel = genericResponse
                    }
                }
            )
            .store(in: &self.subscriptions)

        createPublisher(dropboxSyncUseCase().dropboxClientStatus)
            .eraseToAnyPublisher()
            .receive(on: DispatchQueue.global(qos: .userInitiated))
            .sink(
                receiveCompletion: { completion in
                    if case .failure = completion {
                        self.isDropboxConnected = false
                    }
                },
                receiveValue: { genericResponse in
                    onMainThread {
                        if (genericResponse == DropboxClientStatus.linked) {
                            self.isDropboxConnected = true
                        } else {
                            self.isDropboxConnected = false
                        }
                    }
                }
            )
            .store(in: &self.subscriptions)

        dropboxSyncUseCase().restoreDropboxClient(
            onError: { uiErrorMessage in
                self.dropboxSyncAction = DropboxSyncAction.ShowError(uiErrorMessage: uiErrorMessage)
            }
        )
    }

    func saveDropboxAuth() {
        dropboxSyncUseCase().saveDropboxAuth(
            onError: { uiErrorMessage in
                self.dropboxSyncAction = DropboxSyncAction.ShowError(uiErrorMessage: uiErrorMessage)
            }
        )
    }

    func unlink() {
        dropboxSyncUseCase().unlink()
    }

    func backup() {
        dropboxSyncAction = DropboxSyncAction.Loading()
        DatabaseHelper().dbClear()
        if let databaseUrl = DatabaseImportExport.getDatabaseURL() {
            do {
                let fileData = try Data(contentsOf: databaseUrl)

                dropboxSyncUseCase().upload(
                    dropboxUploadParam: DropboxUploadParam(
                        path: "/\(SchemaKt.DB_FILE_NAME_WITH_EXTENSION)",
                        data: fileData
                    ),
                    onSuccess: {
                        self.reloadDatabase()
                        onMainThread {
                            self.dropboxSyncAction = DropboxSyncAction.Success(message: "dropbox_upload_success".localized)
                            print("Upload success!")
                        }
                    },
                    onError: { uiErrorMessage in
                        self.reloadDatabase()
                        self.dropboxSyncAction = DropboxSyncAction.ShowError(uiErrorMessage: uiErrorMessage)
                    }
                )

            } catch let error {
                // TODO: show error on UI
                print("Unable to load com.prof18.moneyflow.data: \(error)")
                self.dropboxSyncAction = DropboxSyncAction.ShowError(
                    uiErrorMessage: UIErrorMessage(
                        message: "database_file_not_found".localized,
                        nerdMessage: "Error during data creationg"
                    )
                )

            }
        } else {
            self.dropboxSyncAction = DropboxSyncAction.ShowError(
                uiErrorMessage: UIErrorMessage(
                    message: "database_file_not_found".localized,
                    nerdMessage: "Error during data creation"
                )
            )
        }
    }

    func restore() {
        dropboxSyncAction = DropboxSyncAction.Loading()
        dropboxSyncUseCase().download(
          downloadParam: DropboxDownloadParam(
                outputName: SchemaKt.DB_FILE_NAME_WITH_EXTENSION,
                path: "/\(SchemaKt.DB_FILE_NAME_WITH_EXTENSION)"
            ),
            onSuccess: { result in
                if let destinationUrl = result.destinationUrl {
                    DatabaseImportExport.replaceDatabase(url: destinationUrl )
                    onMainThread {
                        self.dropboxSyncAction = DropboxSyncAction.Success(message: "dropbox_download_success".localized)
                        print("Database correct restore")
                    }
                } else {
                    // TODO: generate an error and show failure
                    print("dest url is null")
                    self.dropboxSyncAction = DropboxSyncAction.ShowError(
                        uiErrorMessage: UIErrorMessage(
                            message: "error_dropbox_download".localized,
                            nerdMessage: "Result destination is null"
                        )
                    )
                }

            },
            onError: { uiErrorMessage in
                self.reloadDatabase()
                self.dropboxSyncAction = DropboxSyncAction.ShowError(uiErrorMessage: uiErrorMessage)
            }
        )
    }

    private func reloadDatabase() {
        DI.reloadDIGraph()
        NotificationCenter.default.post(name: .databaseReloaded, object: nil)
    }

    deinit {
        dropboxSyncUseCase().onDestroy()
    }
}
