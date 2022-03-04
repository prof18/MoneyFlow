//
//  DispatcherQueueUtils.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 02/05/21.
//

import Foundation

func onMainThread(_  function: @escaping () -> Void) {
    DispatchQueue.main.async {
        function()
    }
}

func onUserInitiatedThread(_ function: @escaping () -> Void) {
    DispatchQueue.global(qos: .userInitiated).async {
        function()
    }
}

func onUtilityThread(_ function: @escaping () -> Void) {
    DispatchQueue.global(qos: .utility).async {
        function()
    }
}
