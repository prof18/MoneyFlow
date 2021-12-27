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
                        self.homeModel = HomeModel.Error(message: "Something wrong here :(")
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
        homeUseCase().deleteTransaction(transactionId: transactionId)
    }
    
    deinit {
        homeUseCase().onDestroy()
    }
}
