//
//  ContentView.swift
//  Shared
//
//  Created by Marco Gomiero on 25/08/2020.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        Text(CommonKt.createApplicationScreenMessage())
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
