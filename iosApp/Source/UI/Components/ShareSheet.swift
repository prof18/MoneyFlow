//
//  ShareSheet.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 17/10/2020.
//

import SwiftUI

struct ShareSheet: UIViewControllerRepresentable {

    typealias Callback = (
        _ activityType: UIActivity.ActivityType?,
        _ completed: Bool,
        _ returnedItems: [Any]?,
        _ error: Error?
    ) -> Void

    let activityItems: [Any]
    let applicationActivities: [UIActivity]?
    let onSelectedCallback: Callback?

    func makeUIViewController(
        context: UIViewControllerRepresentableContext<ShareSheet>
    ) -> UIActivityViewController {
        let controller = UIActivityViewController(
            activityItems: activityItems,
            applicationActivities: applicationActivities
        )
        controller.completionWithItemsHandler = onSelectedCallback
        return controller
    }

    func updateUIViewController(
        _ uiViewController: UIActivityViewController,
        context: UIViewControllerRepresentableContext<ShareSheet>) {
        }
}
