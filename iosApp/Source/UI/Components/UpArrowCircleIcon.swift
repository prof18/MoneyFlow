//
//  UpArrowCircleIcon.swift
//  iosApp
//
//  Created by Marco Gomiero on 12/03/21.
//

import SwiftUI

struct UpArrowCircleIcon: View {

    var size: CGFloat

    var body: some View {
        Image("ic_arrow_up_solid")
            .resizable()
            .renderingMode(.template)
            .foregroundColor(Color.upArrowColor)
            .rotationEffect(.degrees(-45))
            .frame(width: size, height: size)
            .padding(AppMargins.small)
            .background(Color.upArrowCircleColor)
            .clipShape(Circle())
    }
}
