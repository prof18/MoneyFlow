//
//  CategoriesScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import SwiftUI
import shared

struct CategoriesScreen: View {
    
    @StateObject var viewModel = CategoriesViewModel()
    @StateObject var addTransactionState: AddTransactionState
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>

    var body: some View {
        VStack {
            if (viewModel.categoriesModel is CategoryModel.Loading) {
                Loader().edgesIgnoringSafeArea(.all)
            } else if (viewModel.categoriesModel is CategoryModel.CategoryState) {
                
                List {
                    ForEach((viewModel.categoriesModel as! CategoryModel.CategoryState).categories, id: \.self) { category in
                        CategoryCard(category: category, onItemClick: {
                            
                            addTransactionState.categoryId = category.id
                            addTransactionState.categoryTitle = category.name
                            addTransactionState.categoryIcon = category.icon.iconName
                            
                            self.presentationMode.wrappedValue.dismiss()
                            
                        })
                            .listRowInsets(EdgeInsets())
                    }
                }
                .listStyle(PlainListStyle())
            }
        }
        .onAppear {
            viewModel.startObserving()
        }.onDisappear {
            viewModel.stopObserving()
        }
        .navigationTitle(Text("Select Category"))
    }
}

//struct CategoriesScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        CategoriesScreen()
//    }
//}
