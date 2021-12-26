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
            // TODO: localize
            Text("Latest Transactions")
                .font(AppFonts.h6)
            Spacer()
            HStack {
                Image(systemName: "chevron.right")
            }
        }
        .onTapGesture(perform: {
            // TODO
        })
        .padding(AppMargins.regular)
    }
}

struct HeaderNavigator_Previews: PreviewProvider {
    static var previews: some View {
        HeaderNavigator()
    }
}
