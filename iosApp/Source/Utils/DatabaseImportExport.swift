//

//  DatabaseExporter.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 05/11/2020.
//

import Foundation
import shared

class DatabaseImportExport {

    static func getDatabaseURL() -> URL? {
        do {
            let fileURL = try FileManager.default
                .url(
                    for: .applicationSupportDirectory,
                       in: .userDomainMask,
                       appropriateFor: nil,
                       create: true
                )
                .appendingPathComponent("databases/MoneyFlowDB")
            return fileURL
        } catch {
            return nil
        }
    }

    static func replaceDatabase(url: URL) {
        do {
            DatabaseHelper().dbClear()

            let fileURL = try FileManager.default
                .url(
                    for: .applicationSupportDirectory,
                       in: .userDomainMask,
                       appropriateFor: nil,
                       create: true
                )
                .appendingPathComponent("databases/MoneyFlowDB")

            _ = try FileManager.default.replaceItemAt(
                fileURL, withItemAt: url,
                backupItemName: "MoneyFlowDB.old",
                options: .usingNewMetadataOnly
            )

            DI.reloadDIGraph()

            onMainThread {
                NotificationCenter.default.post(name: .databaseReloaded, object: nil)
            }

        } catch let error {
            print(error.localizedDescription)
            print("Something wrong during replace of the database")
            DI.reloadDIGraph()
        }
    }
}
