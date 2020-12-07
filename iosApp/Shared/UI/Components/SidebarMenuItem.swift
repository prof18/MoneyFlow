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
//                .font(.rSidebarMenu)
                .foregroundColor(.blue)
                .padding(.leading, 8)
                .padding(.trailing, 12)
            Text(textString)
//                .font(.rSidebarMenu)
                .foregroundColor(.black)
        }
        .frame(minWidth: 0,
               maxWidth: .infinity,
               minHeight: 50,
               maxHeight: 50,
               alignment: .leading
        )
        .contentShape(Rectangle())
        .background(currentSelection == itemSelection ? Color.black.opacity(0.1) : Color.clear)
        .cornerRadius(8)
        .padding(.horizontal, 16)
    }
}
