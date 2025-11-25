//
//  DropboxDataSourceIOS.swift
//  MoneyFlow
//
//  Created by Marco Gomiero on 11.12.22.
//

import SwiftyDropbox
import Foundation
import shared

final class DropboxDataSourceIOS: DropboxDataSource {

    private let IOS_DROPBOX_CREDENTIALS = "IOS_DROPBOX_CREDENTIALS"
    private var client: DropboxClient?

    func setup(apiKey: String) {
        DropboxClientsManager.setupWithAppKey(apiKey)
    }

    func startAuthorization(platformAuthHandler: @escaping () -> Void) {
        platformAuthHandler()
    }

    func handleOAuthResponse(platformOAuthResponseHandler: @escaping () -> Void) {
        platformOAuthResponseHandler()
    }

    func isClientSet() -> Bool {
        client != nil
    }

    func performDownload(downloadParam: DropboxDownloadParam, completionHandler: @escaping (DropboxDownloadResult?, Error?) -> Void) {
        let fileManager = FileManager.default
        let directoryURL = fileManager.urls(for: .documentDirectory, in: .userDomainMask)[0]
        let destURL = directoryURL.appendingPathComponent(downloadParam.outputName)
        let destination: (URL, HTTPURLResponse) -> URL = { _, _ in
            destURL
        }

        if let client = client {
            client.files.download(path: downloadParam.path, overwrite: true, destination: destination)
                    .response { response, error in
                        if let response = response {
                            print("Data successfully downloaded from Dropbox")
                            let downloadResult = DropboxDownloadResult(
                                    id: response.0.id,
                                    sizeInByte: Int64(response.0.size),
                                    contentHash: response.0.contentHash,
                                    destinationUrl: response.1
                            )
                            completionHandler(downloadResult, nil)

                        } else if let error = error {
                            print(error)
                            completionHandler(nil, DropboxErrors.downloadError(reason: error.description))
                        }
                    }
        } else {
            completionHandler(nil, DropboxErrors.downloadError(reason: "The client is nil"))

        }
    }

    func performUpload(uploadParam: DropboxUploadParam, completionHandler: @escaping (DropboxUploadResult?, Error?) -> Void) {
        if let client = client {
            client.files.upload(
                            path: uploadParam.path,
                            mode: .overwrite,
                            input: uploadParam.data
                    )
                    .response { response, error in
                        if let response = response {
                            print("Data successfully uploaded to Dropbox")

                            let uploadResult = DropboxUploadResult(
                                    id: response.id,
                                    editDateMillis: Int64(response.serverModified.timeIntervalSince1970 * 1000),
                                    sizeInByte: Int64(response.size),
                                    contentHash: response.contentHash
                            )
                            completionHandler(uploadResult, nil)
                        } else if let error = error {
                            print(error)
                            completionHandler(nil, DropboxErrors.uploadError(reason: error.description))
                        }
                    }
        } else {
            completionHandler(nil, DropboxErrors.uploadError(reason: "The client is nil"))
        }
    }

    func restoreAuth(stringCredentials: DropboxStringCredentials) {
        if client != nil {
            return
        }
        client = createClient()
    }

    func revokeAccess() {
        DropboxClientsManager.unlinkClients()
        client = nil
    }

    func saveAuth() -> DropboxStringCredentials {
        DropboxStringCredentials(value: "todo")
    }

    private func createClient() -> DropboxClient? {
        DropboxClientsManager.authorizedClient
    }

    static func startAuth(viewController: UIViewController) {
        let scopeRequest = ScopeRequest(
                scopeType: .user,
                scopes: DropboxConstants.shared.DROPBOX_SCOPES,
                includeGrantedScopes: false
        )

        DropboxClientsManager.authorizeFromControllerV2(
                UIApplication.shared,
                controller: viewController,
                loadingStatusDelegate: nil,
                openURL: { url in
                    UIApplication.shared.open(url)
                },
                scopeRequest: scopeRequest
        )
    }

    static func handleOAuthResponse(
            url: URL,
            onSuccess: @escaping () -> Void,
            onCancel: @escaping () -> Void,
            onError: @escaping () -> Void
    ) {
        let oauthCompletion: DropboxOAuthCompletion = {
            if let authResult = $0 {
                switch authResult {
                case .success:
                    print("Success! User is logged into DropboxClientsManager.")
                    onSuccess()
                case .cancel:
                    print("Authorization flow was manually canceled by user!")
                    onCancel()
                case .error(_, let description):
                    print("Error during dropbox auth:: \(String(describing: description))")
                    onError()
                }
            }
        }
        DropboxClientsManager.handleRedirectURL(url, completion: oauthCompletion)
    }
}
