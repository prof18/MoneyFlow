//
//  DMImage.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 06/12/2020.
//
import SwiftUI

struct DMImage: View {
    
    var imageName: String
    @Environment(\.colorScheme) var colorScheme
    
    init(_ imageName: String) {
        self.imageName = imageName
    }
    
    var body: some View {
        if colorScheme == .dark {
            Image(imageName)
                .renderingMode(.template)
                .foregroundColor(.white)
        } else {
            Image(imageName)
                .renderingMode(.template)
                .foregroundColor(.black)
        }
    }
    
}
