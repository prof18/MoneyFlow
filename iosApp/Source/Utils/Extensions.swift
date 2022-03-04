//
//  Extensions.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI

extension Color {

    init(hex: String) {

        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)

        var int: UInt64 = 0

        Scanner(string: hex).scanHexInt64(&int)

        let alpha, red, green, blue: UInt64

        switch hex.count {
        case 3: // RGB (12-bit)
            (alpha, red, green, blue) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (alpha, red, green, blue) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (alpha, red, green, blue) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (alpha, red, green, blue) = (1, 1, 1, 0)
        }

        self.init(
            .sRGB,
            red: Double(red) / 255,
            green: Double(green) / 255,
            blue:  Double(blue) / 255,
            opacity: Double(alpha) / 255
        )
    }

}

extension Double {
    func formatTwoDigit() -> String {
        return String(format: "%\(".2")f", self)
    }
}

extension Date {
    func millisecondsSinceEpoch() -> Int64 {
        return Int64((self.timeIntervalSince1970 * 1000.0).rounded())
    }
}

extension String {

    var localized: String {
        return NSLocalizedString(self, comment: "")
    }
}
