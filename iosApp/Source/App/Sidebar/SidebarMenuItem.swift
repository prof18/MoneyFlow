//
//  SidebarMenuItem.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 07/12/2020.
//

import SwiftUI

struct SidebarMenuItem: View {

    @Binding var currentSelection: SidebarNavigationItem?

    @State var imageName: String

    @State var textString: String

    @State var itemSelection: SidebarNavigationItem

    var body: some View {
        HStack {
            Image(systemName: imageName)
                .font(AppFonts.sidebarFont)
                .foregroundColor(.blue)
                .padding(.leading, AppMargins.small)
                .frame(width: 40, height: 40)

            Text(textString)
                .padding(.leading, AppMargins.small)
                .font(AppFonts.sidebarFont)
                .foregroundColor(.colorOnBackground)
        }
        .frame(minWidth: 0,
               maxWidth: .infinity,
               minHeight: 50,
               maxHeight: 50,
               alignment: .leading
        )
        .contentShape(Rectangle())
        .background(currentSelection == itemSelection ? Color.primaryColor.opacity(0.2) : Color.clear)
        .cornerRadius(8)
        .padding(.horizontal, 16)
    }
}
