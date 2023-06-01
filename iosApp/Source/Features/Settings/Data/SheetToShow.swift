//
//  SheetToShow.swift
//  iosApp
//
//  Created by Marco Gomiero on 08/12/2020.
//

import Foundation

enum SheetToShow: Identifiable {
    case filePicker
    case shareSheet

    var id: Int {
        hashValue
}
}
