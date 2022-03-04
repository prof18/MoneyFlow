//
//  CategoryCard.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import SwiftUI
import shared

struct CategoryCard: View {

    let category: shared.Category
    let onItemClick: () -> Void

    var body: some View {
        HStack(alignment: .center) {

            DMImage(imageName: category.icon.iconName)
                .padding(AppMargins.small)
                .background(Color.primaryColor)
                .cornerRadius(AppMargins.regularCornerRadius)
                .padding(AppMargins.regular)

            Text(category.name)
                .font(AppFonts.subtitle1)

            Spacer()
        }
        .contentShape(Rectangle())
        .onTapGesture {
            onItemClick()
        }
    }
}

// struct CategoryCard_Previews: PreviewProvider {
//    static var previews: some View {
//        CategoryCard()
//    }
// }
