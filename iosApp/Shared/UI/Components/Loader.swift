//
//  Loader.swift
//  iosApp
//
//  Created by Marco Gomiero on 05/09/2020.
//

import SwiftUI

struct Loader: UIViewRepresentable {

    let style: UIActivityIndicatorView.Style = .large

    func makeUIView(context: UIViewRepresentableContext<Loader>) -> UIActivityIndicatorView {
        return UIActivityIndicatorView(style: style)
    }

    func updateUIView(_ uiView: UIActivityIndicatorView, context: UIViewRepresentableContext<Loader>) {
        uiView.startAnimating()
    }
}
