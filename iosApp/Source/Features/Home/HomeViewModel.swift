//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Marco Gomiero on 05/09/2020.
//

import shared
import Combine

class HomeViewModel: ObservableObject {
    
    @Published var homeModel: HomeModel = HomeModel.Loading()
    
    private var subscriptions = Set<AnyCancellable>()
    
    private func homeUseCase() -> HomeUseCaseIos {
        DI.getHomeUseCase()
    }
    
    func startObserving() {
        createPublisher(homeUseCase().getMoneySummary())
            .eraseToAnyPublisher()
            .receive(on: DispatchQueue.global(qos: .userInitiated))
            .sink(
                receiveCompletion: { completion in
                    if case let .failure(error) = completion {
                        let moneyFlowError = MoneyFlowError.GetMoneySummary(throwable:  error.throwable)
                        error.throwable.logError(moneyFlowError: moneyFlowError , message: "Got error while transforming Flow to Publisher")
                        self.homeModel = HomeModel.Error(error: moneyFlowError)
                    }
                },
                receiveValue: { genericResponse in
                    onMainThread {
                        self.homeModel = genericResponse
                    }
                }
            )
            .store(in: &self.subscriptions)
    }
    
    func deleteTransaction(transactionId: Int64) {
        homeUseCase().deleteTransaction(
            transactionId: transactionId,
            onError: { error in
                // TODO: show the error on the UI
            }
        )
    }
    
    deinit {
        homeUseCase().onDestroy()
    }
}
