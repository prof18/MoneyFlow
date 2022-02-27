//
//  RadioButton.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 06/12/2020.
//
import SwiftUI

struct RadioButton: View {

    @Environment(\.colorScheme) var colorScheme

    let id: String
    let callback: (String) -> Void
    let selectedID : String
    let size: CGFloat
    let color: Color
    let textSize: CGFloat

    init(
        _ id: String,
        callback: @escaping (String) -> Void,
        selectedID: String,
        size: CGFloat = 20,
        color: Color = Color.primaryColor,
        textSize: CGFloat = 14
        ) {
        self.id = id
        self.size = size
        self.color = color
        self.textSize = textSize
        self.selectedID = selectedID
        self.callback = callback
    }

    var body: some View {
        Button(action: {
            self.callback(self.id)
        }) {
            HStack(alignment: .center, spacing: 10) {
                Image(systemName: self.selectedID == self.id ? "largecircle.fill.circle" : "circle")
                    .renderingMode(.original)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: self.size, height: self.size)
                    .modifier(ColorInvert())
                Text(id)
                    .font(Font.system(size: textSize))
                Spacer()
            }.foregroundColor(self.color)
        }
        .foregroundColor(self.color)
    }
}
