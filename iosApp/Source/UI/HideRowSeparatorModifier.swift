//
//  HideRowSeparatorModifier.swift
//  iosApp
//
//  Created by Marco Gomiero on 30/08/2020.
//

import SwiftUI
struct HideRowSeparatorModifier: ViewModifier {

  static let defaultListRowHeight: CGFloat = 44
  var insets: EdgeInsets
  var background: Color

  init(insets: EdgeInsets, background: Color) {
    self.insets = insets
    var alpha: CGFloat = 0
    UIColor(background).getWhite(nil, alpha: &alpha)
    self.background = background
  }

  func body(content: Content) -> some View {
    content
      .padding(insets)
      .frame(
        minWidth: 0, maxWidth: .infinity,
        minHeight: Self.defaultListRowHeight,
        alignment: .leading
      )
      .listRowInsets(EdgeInsets())
      .background(background)
  }
}

extension EdgeInsets {
  static let defaultListRowInsets = Self(top: 0, leading: 16, bottom: 0, trailing: 16)
}

extension View {
  func hideRowSeparator(
    insets: EdgeInsets = .defaultListRowInsets,
    background: Color = .white
  ) -> some View {
    modifier(HideRowSeparatorModifier(
      insets: insets,
      background: background
    ))
  }
}

struct HideRowSeparator_Previews: PreviewProvider {
  static var previews: some View {
    List {
      ForEach(0..<10) { _ in
        Text("Text")
          .hideRowSeparator()
      }
    }
    .previewLayout(.sizeThatFits)
  }
}
