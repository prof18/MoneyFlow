//
//  DropboxController.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 16/12/2020.
//

import Foundation
import SwiftUI
import SwiftyDropbox


struct DropboxLoginView: UIViewControllerRepresentable {
    
    @Binding var isShown : Bool

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        if isShown {
            let scopeRequest = ScopeRequest(scopeType: .user, scopes: ["files.content.write", "files.content.read", "files.metadata.read"], includeGrantedScopes: false)
              DropboxClientsManager.authorizeFromControllerV2(
                  UIApplication.shared,
                  controller: uiViewController,
                  loadingStatusDelegate: nil,
                openURL: { (url: URL) -> Void in UIApplication.shared.open(url, options: [:], completionHandler: nil) },
                  scopeRequest: scopeRequest
              )
        }
    }

    func makeUIViewController(context _: Self.Context) -> UIViewController {
        return UIViewController()
    }
}
