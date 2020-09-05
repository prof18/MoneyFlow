//
//  Loader.swift
//  iosApp
//
//  Created by Marco Gomiero on 05/09/2020.
//

import SwiftUI

struct Loader : View {
        
    @SwiftUI.State var animate = false
    
    var body: some View {
        
        VStack(spacing: 28) {
            
            Circle()
                // Dark mode adoption
                .stroke(AngularGradient(gradient: .init(colors: [Color.primary,Color.primary.opacity(0)]), center: .center))
                .frame(width: 50, height: 50)
                // Animation
                .rotationEffect(.init(degrees: animate ? 360 : 0))
        }
        .padding(.vertical, AppMargins.regular)
        .padding(.horizontal, AppMargins.medium)
        .background(BlurView())
        .cornerRadius(AppMargins.mediumCornerRadius)
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(
            Color.primary.opacity(0.35)
        )
        .onAppear {
            // Starting animation
            withAnimation(Animation.linear(duration: 1.5).repeatForever(autoreverses: false)) {
                self.animate.toggle()
            }
        }
        
    }
    
}

struct BlurView : UIViewRepresentable {
    
    func makeUIView(context: Context) -> UIVisualEffectView {
        let view = UIVisualEffectView(effect: UIBlurEffect(style: .systemThinMaterial))
        
        return view
    }
    
    func updateUIView(_ uiView: UIVisualEffectView, context: Context) {
        
    }
    
}

struct Loader_Previews: PreviewProvider {
    static var previews: some View {
       Loader()
    }
}
