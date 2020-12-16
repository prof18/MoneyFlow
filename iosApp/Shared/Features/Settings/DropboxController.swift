//
//  DropboxController.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 16/12/2020.
//

import Foundation
import SwiftUI
import SwiftyDropbox


struct DropboxView: UIViewControllerRepresentable {
    
    @Binding var isShown : Bool

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        if isShown {
            DropboxClientsManager.authorizeFromController(UIApplication.shared,
            controller: uiViewController,
            openURL: { (url: URL) -> Void in
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            })
        }
    }

    func makeUIViewController(context _: Self.Context) -> UIViewController {
        return UIViewController()
    }
}
