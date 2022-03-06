//
//  CombineAdapters.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 02/05/21.
//
//  From https://github.com/russhwolf/To-Do/blob/master/iosApp/ToDo/CombineAdapters.swift
//

import Combine
import shared

func createPublisher<T>(_ flowAdapter: FlowWrapper<T>) -> AnyPublisher<T, KotlinError> {
//    return Deferred<Publishers.HandleEvents<PassthroughSubject<T, KotlinError>>> {
        let subject = PassthroughSubject<T, KotlinError>()
        let job = flowAdapter.subscribe { (item) in
            subject.send(item)
        } onError: { (error) in
            subject.send(completion: .failure(KotlinError(error)))
        } onComplete: {
            subject.send(completion: .finished)
        }
        return subject.handleEvents(receiveCancel: {
            job.cancel(cause: nil)
        }).eraseToAnyPublisher()
//    }.eraseToAnyPublisher()
}

class PublishedFlow<T> : ObservableObject {
    @Published
    var output: T

    init<E>(_ publisher: AnyPublisher<T, E>, defaultValue: T) {
        output = defaultValue

        publisher
            .replaceError(with: defaultValue)
            .compactMap { $0 }
            .receive(on: DispatchQueue.main)
            .assign(to: &$output)
    }
}

class KotlinError: LocalizedError {
    let throwable: KotlinThrowable
    init(_ throwable: KotlinThrowable) {
        self.throwable = throwable
    }
    var errorDescription: String? {
        // swiftlint:disable implicit_getter
        get { throwable.message }
        // swiftlint:enable implicit_getter
    }
}
