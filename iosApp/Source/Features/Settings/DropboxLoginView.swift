//
//  DropboxController.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 16/12/2020.
//

import Foundation
import SwiftUI
import shared

struct DropboxLoginView: UIViewControllerRepresentable {

    @Binding var isShown : Bool

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        if isShown {
            DI.getDropboxSyncUseCase().startAuthFlow(
                authorizationParam: DropboxAuthorizationParam(
                    viewController: uiViewController,
                    scopes: DropboxConstants().DROPBOX_SCOPES
                )
            )
        }
    }

    func makeUIViewController(context _: Self.Context) -> UIViewController {
        return UIViewController()
    }
}
