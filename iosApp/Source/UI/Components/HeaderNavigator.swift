//
//  HeaderNavigator.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI

struct HeaderNavigator: View {
    var body: some View {
        HStack {
            Text("latest_transactions".localized)
                .font(AppFonts.h6)
            Spacer()
            HStack {
                Image(systemName: "chevron.right")
            }
        }
        .onTapGesture(perform: {
            // TODO: add action to navigate
        })
        .padding(AppMargins.regular)
    }
}

struct HeaderNavigator_Previews: PreviewProvider {
    static var previews: some View {
        HeaderNavigator()
    }
}
