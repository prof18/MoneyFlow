//
//  CategoriesViewModel.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import shared

class CategoriesViewModel: ObservableObject {
    
    @Published var categoriesModel: CategoryModel = CategoryModel.Loading()
    
    var useCase: CategoriesUseCase?
    
    func startObserving() {
        useCase = CategoriesUseCaseImpl(moneyRepository: DIContainer.instance.getMoneyRepository(), viewUpdate: { [weak self] model in
            self?.categoriesModel = model
        })
        self.useCase?.getCategories()
    }
    
    func stopObserving() {
        self.useCase?.onDestroy()
    }
}
