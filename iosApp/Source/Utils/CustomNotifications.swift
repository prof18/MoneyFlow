//
//  DropboxNotifications.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 17/12/2020.
//

import Foundation

extension Notification.Name {
    static let didDropboxSuccess = Notification.Name("didDropboxSuccess")
    static let didDropboxCancel = Notification.Name("didDropboxCancel")
    static let didDropboxError = Notification.Name("didDropboxError")
    static let databaseReloaded = Notification.Name("databaseReloaded")
}
