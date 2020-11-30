//
//  AddTransactionScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import SwiftUI

struct AddTransactionScreen: View {
    
    @Binding var showSheet: Bool
    @StateObject var addTransactionState = AddTransactionState()
    
    var body: some View {
        NavigationView {
            VStack {
                
                Text("Selected Category: \(addTransactionState.categoryTitle ?? "Not yet selected")")
                
                NavigationLink(destination: CategoriesScreen(addTransactionState: addTransactionState)) {
                    Text("Categories")
                }
            }
            .navigationTitle(Text("Add transaction"))
            .navigationBarItems(trailing: Button(action: {
                self.showSheet.toggle()
            }) {
                // TODO: localize
                Text("Save")
                
            })
        }
       
    }
}

//struct AddTransactionScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        AddTransactionScreen()
//    }
//}
