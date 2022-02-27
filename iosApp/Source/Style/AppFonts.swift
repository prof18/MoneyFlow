//
//  AppFonts.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI

enum PoppinsFontType: String {

    case semibold = "Poppins-Semibold"
    case extraLight = "Poppins-ExtraLight"
    case light = "Poppins-Light"
    case regular = "Poppins-Regular"

}

// swiftlint:disable identifier_name
struct AppFonts {

    static var h3: Font {
        return Font.custom(PoppinsFontType.regular.rawValue, size: 48)
    }

    static var h5: Font {
        return Font.custom(PoppinsFontType.regular.rawValue, size: 24)
    }

    static var sidebarFont: Font {
        return Font.custom(PoppinsFontType.regular.rawValue, size: 20)
    }

    static var h6: Font {
        return Font.custom(PoppinsFontType.semibold.rawValue, size: 20)
    }

    static var subtitle1: Font {
        return Font.custom(PoppinsFontType.semibold.rawValue, size: 16)
    }

    static var subtitle2: Font {
        return Font.custom(PoppinsFontType.light.rawValue, size: 16)
    }

    static var caption: Font {
        return Font.custom(PoppinsFontType.extraLight.rawValue, size: 12)
    }

    static var body1: Font {
        return Font.custom(PoppinsFontType.regular.rawValue, size: 16)
    }

}
// swiftlint:enable identifier_name
