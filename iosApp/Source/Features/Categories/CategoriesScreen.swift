//
//  CategoriesScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import SwiftUI
import shared

struct CategoriesScreen: View {
    
    @EnvironmentObject var appState: AppState
    @StateObject var addTransactionState: AddTransactionState
    @StateObject var viewModel = CategoriesViewModel()


    var body: some View {
        CategoriesScreenContent(
            categoriesModel: $viewModel.categoriesModel,
            appErrorData: $appState.errorData,
            addTransactionState: addTransactionState,
            onAppear: { viewModel.startObserving() } ,
            onDisappear: { viewModel.stopObserving() }
        )
    }
    
}

struct CategoriesScreenContent: View {
    
    @Binding var categoriesModel: CategoryModel
    @Binding var appErrorData: UIErrorData
    @StateObject var addTransactionState: AddTransactionState
    
    let onAppear  : () -> Void
    let onDisappear  : () -> Void
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        VStack {
                        
            if (categoriesModel is CategoryModel.Loading) {
                Loader().edgesIgnoringSafeArea(.all)
            } else if let error = categoriesModel as? CategoryModel.Error {
                ErrorView(uiErrorMessage: error.uiErrorMessage)
            } else if let categoryState = categoriesModel as? CategoryModel.CategoryState {
                
                List {
                    ForEach(categoryState.categories, id: \.self) { category in
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
            onAppear()
        }.onDisappear {
            onDisappear()
        }
        .onChange(of: self.categoriesModel) { model  in
            if model is CategoryModel.Error {
                let uiErrorMessage = (model as! CategoryModel.Error).uiErrorMessage
                self.appErrorData = uiErrorMessage.toUIErrorData()
            }
        }
        .navigationTitle(Text("Select Category")) // TODO: localize
    }
    
}

struct CategoriesScreen_Previews: PreviewProvider {
    
    static let addTransactionState = AddTransactionState()
    
    static var previews: some View {
        CategoriesScreenContent(
            categoriesModel: .constant(CategoryModel.CategoryState(
                categories: [
                    shared.Category(id: 1, name: "Category 1", icon: CategoryIcon.icAddressBook),
                    shared.Category(id: 2, name: "Category 2", icon: CategoryIcon.icAdjustSolid)
                ]
            )),
            appErrorData: .constant(UIErrorData(title: "An error occoured", nerdishDesc: "Error code 1012", showBanner: true )),
            addTransactionState: addTransactionState,
            onAppear: {},
            onDisappear: {}
        )
        
        CategoriesScreenContent(
            categoriesModel: .constant(CategoryModel.Loading()),
            appErrorData: .constant(UIErrorData(title: "An error occoured", nerdishDesc: "Error code 1012", showBanner: true )),
            addTransactionState: addTransactionState,
            onAppear: {},
            onDisappear: {}
        )
        
        CategoriesScreenContent(
            categoriesModel: .constant(CategoryModel.Error(uiErrorMessage: UIErrorMessage(message: "Error", nerdMessage: "Error code: 101"))),
            appErrorData: .constant(UIErrorData(title: "An error occoured", nerdishDesc: "Error code 1012", showBanner: true )),
            addTransactionState: addTransactionState,
            onAppear: {},
            onDisappear: {}
        )
    }
}
