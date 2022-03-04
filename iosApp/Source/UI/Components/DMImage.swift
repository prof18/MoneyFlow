//
//  DMImage.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 06/12/2020.
//
import SwiftUI

struct DMImage: View {

    var imageName: String
    var color: Color = Color.onPrimary
    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        Image(imageName)
            .renderingMode(.template)
            .foregroundColor(color)
    }

}
