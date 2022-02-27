//
//  BottomErrorBannerr.swift
//  MoneyFlow
//
//  Created by Marco Gomiero on 28.12.21.
//

import SwiftUI

struct BottomErrorBanner: View {

    @StateObject var appState: AppState

    @State private var errorData: UIErrorData = UIErrorData()

    @State private var showBanner: Bool = false

    var body: some View {

        VStack(alignment: .leading, spacing: AppMargins.xSmall) {

            Text(errorData.title)
                .font(AppFonts.subtitle1)
                .foregroundColor(Color.popupText)

            Text(errorData.nerdishDesc)
                .font(AppFonts.caption)
                .foregroundColor(Color.popupText)

        }
        .padding(.vertical, AppMargins.regular)
        .padding(.horizontal, AppMargins.medium)
        .background(Color.popupBackground)
        .shadow(radius: 10)
        .cornerRadius(8)
        .transition(AnyTransition.move(edge: .bottom).combined(with: .opacity))
        .animation(.spring())
        .gesture(
            DragGesture()
                .onChanged { _ in
                    withAnimation {
                        showBanner = false
                    }
                }
        )
        .onReceive(self.appState.$errorData) { value in
            if !value.isEmpty() {
                withAnimation {
                    self.errorData = value
                    self.showBanner = true
                }

                DispatchQueue.main.asyncAfter(deadline: .now() + 5.0) {
                    withAnimation {
                        showBanner = false
                    }
                }
            }
        }
        .padding(.bottom, AppMargins.regular)
        .zIndex(100)
        .offset(y: showBanner ? 0 : UIScreen.main.bounds.height)

    }
}
