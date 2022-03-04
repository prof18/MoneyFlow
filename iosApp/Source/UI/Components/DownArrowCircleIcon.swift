//
//  DownArrowCircleIcon.swift
//  iosApp
//
//  Created by Marco Gomiero on 12/03/21.
//

import SwiftUI

struct DownArrowCircleIcon: View {

    var size: CGFloat

    var body: some View {
        Image("ic_arrow_down_solid")
            .resizable()
            .renderingMode(.template)
            .foregroundColor(Color.downArrowColor)
            .rotationEffect(.degrees(-45))
            .frame(width: size, height: size)
            .padding(AppMargins.small)
            .background(Color.downArrowCircleColor)
            .clipShape(Circle())
    }
}
