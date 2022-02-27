//
//  ColorInverter.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 06/12/2020.
//

import SwiftUI

struct ColorInvert: ViewModifier {

    @Environment(\.colorScheme) var colorScheme

    func body(content: Content) -> some View {
        Group {
            if colorScheme == .dark {
                content.colorInvert()
            } else {
                content
            }
        }
    }
}
