//
// Created by Marco Gomiero on 11.12.22.
//

import Foundation

enum DropboxErrors: Error {
    case uploadError(reason: String)
    case downloadError(reason: String)
}
