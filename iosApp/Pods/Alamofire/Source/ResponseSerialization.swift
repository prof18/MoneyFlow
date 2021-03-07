//
//  ResponseSerialization.swift
//
//  Copyright (c) 2014 Alamofire Software Foundation (http://alamofire.org/)
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.
//

import Foundation

/// The type in which all com.prof18.moneyflow.data response serializers must conform to in order to serialize a response.
public protocol DataResponseSerializerProtocol {
    /// The type of serialized object to be created by this `DataResponseSerializerType`.
    associatedtype SerializedObject

    /// A closure used by response handlers that takes a request, response, com.prof18.moneyflow.data and error and returns a result.
    var serializeResponse: (URLRequest?, HTTPURLResponse?, Data?, Error?) -> Result<SerializedObject> { get }
}

// MARK: -

/// A generic `DataResponseSerializerType` used to serialize a request, response, and com.prof18.moneyflow.data into a serialized object.
public struct DataResponseSerializer<Value>: DataResponseSerializerProtocol {
    /// The type of serialized object to be created by this `DataResponseSerializer`.
    public typealias SerializedObject = Value

    /// A closure used by response handlers that takes a request, response, com.prof18.moneyflow.data and error and returns a result.
    public var serializeResponse: (URLRequest?, HTTPURLResponse?, Data?, Error?) -> Result<Value>

    /// Initializes the `ResponseSerializer` instance with the given serialize response closure.
    ///
    /// - parameter serializeResponse: The closure used to serialize the response.
    ///
    /// - returns: The new generic response serializer instance.
    public init(serializeResponse: @escaping (URLRequest?, HTTPURLResponse?, Data?, Error?) -> Result<Value>) {
        self.serializeResponse = serializeResponse
    }
}

// MARK: -

/// The type in which all download response serializers must conform to in order to serialize a response.
public protocol DownloadResponseSerializerProtocol {
    /// The type of serialized object to be created by this `DownloadResponseSerializerType`.
    associatedtype SerializedObject

    /// A closure used by response handlers that takes a request, response, url and error and returns a result.
    var serializeResponse: (URLRequest?, HTTPURLResponse?, URL?, Error?) -> Result<SerializedObject> { get }
}

// MARK: -

/// A generic `DownloadResponseSerializerType` used to serialize a request, response, and com.prof18.moneyflow.data into a serialized object.
public struct DownloadResponseSerializer<Value>: DownloadResponseSerializerProtocol {
    /// The type of serialized object to be created by this `DownloadResponseSerializer`.
    public typealias SerializedObject = Value

    /// A closure used by response handlers that takes a request, response, url and error and returns a result.
    public var serializeResponse: (URLRequest?, HTTPURLResponse?, URL?, Error?) -> Result<Value>

    /// Initializes the `ResponseSerializer` instance with the given serialize response closure.
    ///
    /// - parameter serializeResponse: The closure used to serialize the response.
    ///
    /// - returns: The new generic response serializer instance.
    public init(serializeResponse: @escaping (URLRequest?, HTTPURLResponse?, URL?, Error?) -> Result<Value>) {
        self.serializeResponse = serializeResponse
    }
}

// MARK: - Timeline

extension Request {
    var timeline: Timeline {
        let requestStartTime = self.startTime ?? CFAbsoluteTimeGetCurrent()
        let requestCompletedTime = self.endTime ?? CFAbsoluteTimeGetCurrent()
        let initialResponseTime = self.delegate.initialResponseTime ?? requestCompletedTime

        return Timeline(
            requestStartTime: requestStartTime,
            initialResponseTime: initialResponseTime,
            requestCompletedTime: requestCompletedTime,
            serializationCompletedTime: CFAbsoluteTimeGetCurrent()
        )
    }
}

// MARK: - Default

extension DataRequest {
    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter queue:             The queue on which the completion handler is dispatched.
    /// - parameter completionHandler: The code to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func response(queue: DispatchQueue? = nil, completionHandler: @escaping (DefaultDataResponse) -> Void) -> Self {
        delegate.queue.addOperation {
            (queue ?? DispatchQueue.main).async {
                var dataResponse = DefaultDataResponse(
                    request: self.request,
                    response: self.response,
                    com.prof18.moneyflow.data: self.delegate.com.prof18.moneyflow.data,
                    error: self.delegate.error,
                    timeline: self.timeline
                )

                dataResponse.add(self.delegate.metrics)

                completionHandler(dataResponse)
            }
        }

        return self
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter queue:              The queue on which the completion handler is dispatched.
    /// - parameter responseSerializer: The response serializer responsible for serializing the request, response,
    ///                                 and com.prof18.moneyflow.data.
    /// - parameter completionHandler:  The code to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func response<T: DataResponseSerializerProtocol>(
        queue: DispatchQueue? = nil,
        responseSerializer: T,
        completionHandler: @escaping (DataResponse<T.SerializedObject>) -> Void)
        -> Self
    {
        delegate.queue.addOperation {
            let result = responseSerializer.serializeResponse(
                self.request,
                self.response,
                self.delegate.com.prof18.moneyflow.data,
                self.delegate.error
            )

            var dataResponse = DataResponse<T.SerializedObject>(
                request: self.request,
                response: self.response,
                com.prof18.moneyflow.data: self.delegate.com.prof18.moneyflow.data,
                result: result,
                timeline: self.timeline
            )

            dataResponse.add(self.delegate.metrics)

            (queue ?? DispatchQueue.main).async { completionHandler(dataResponse) }
        }

        return self
    }
}

extension DownloadRequest {
    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter queue:             The queue on which the completion handler is dispatched.
    /// - parameter completionHandler: The code to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func response(
        queue: DispatchQueue? = nil,
        completionHandler: @escaping (DefaultDownloadResponse) -> Void)
        -> Self
    {
        delegate.queue.addOperation {
            (queue ?? DispatchQueue.main).async {
                var downloadResponse = DefaultDownloadResponse(
                    request: self.request,
                    response: self.response,
                    temporaryURL: self.downloadDelegate.temporaryURL,
                    destinationURL: self.downloadDelegate.destinationURL,
                    resumeData: self.downloadDelegate.resumeData,
                    error: self.downloadDelegate.error,
                    timeline: self.timeline
                )

                downloadResponse.add(self.delegate.metrics)

                completionHandler(downloadResponse)
            }
        }

        return self
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter queue:              The queue on which the completion handler is dispatched.
    /// - parameter responseSerializer: The response serializer responsible for serializing the request, response,
    ///                                 and com.prof18.moneyflow.data contained in the destination url.
    /// - parameter completionHandler:  The code to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func response<T: DownloadResponseSerializerProtocol>(
        queue: DispatchQueue? = nil,
        responseSerializer: T,
        completionHandler: @escaping (DownloadResponse<T.SerializedObject>) -> Void)
        -> Self
    {
        delegate.queue.addOperation {
            let result = responseSerializer.serializeResponse(
                self.request,
                self.response,
                self.downloadDelegate.fileURL,
                self.downloadDelegate.error
            )

            var downloadResponse = DownloadResponse<T.SerializedObject>(
                request: self.request,
                response: self.response,
                temporaryURL: self.downloadDelegate.temporaryURL,
                destinationURL: self.downloadDelegate.destinationURL,
                resumeData: self.downloadDelegate.resumeData,
                result: result,
                timeline: self.timeline
            )

            downloadResponse.add(self.delegate.metrics)

            (queue ?? DispatchQueue.main).async { completionHandler(downloadResponse) }
        }

        return self
    }
}

// MARK: - Data

extension Request {
    /// Returns a result com.prof18.moneyflow.data type that contains the response com.prof18.moneyflow.data as-is.
    ///
    /// - parameter response: The response from the server.
    /// - parameter com.prof18.moneyflow.data:     The com.prof18.moneyflow.data returned from the server.
    /// - parameter error:    The error already encountered if it exists.
    ///
    /// - returns: The result com.prof18.moneyflow.data type.
    public static func serializeResponseData(response: HTTPURLResponse?, com.prof18.moneyflow.data: Data?, error: Error?) -> Result<Data> {
        guard error == nil else { return .failure(error!) }

        if let response = response, emptyDataStatusCodes.contains(response.statusCode) { return .success(Data()) }

        guard let validData = com.prof18.moneyflow.data else {
            return .failure(AFError.responseSerializationFailed(reason: .inputDataNil))
        }

        return .success(validData)
    }
}

extension DataRequest {
    /// Creates a response serializer that returns the associated com.prof18.moneyflow.data as-is.
    ///
    /// - returns: A com.prof18.moneyflow.data response serializer.
    public static func dataResponseSerializer() -> DataResponseSerializer<Data> {
        return DataResponseSerializer { _, response, com.prof18.moneyflow.data, error in
            return Request.serializeResponseData(response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter completionHandler: The code to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responseData(
        queue: DispatchQueue? = nil,
        completionHandler: @escaping (DataResponse<Data>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DataRequest.dataResponseSerializer(),
            completionHandler: completionHandler
        )
    }
}

extension DownloadRequest {
    /// Creates a response serializer that returns the associated com.prof18.moneyflow.data as-is.
    ///
    /// - returns: A com.prof18.moneyflow.data response serializer.
    public static func dataResponseSerializer() -> DownloadResponseSerializer<Data> {
        return DownloadResponseSerializer { _, response, fileURL, error in
            guard error == nil else { return .failure(error!) }

            guard let fileURL = fileURL else {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileNil))
            }

            do {
                let com.prof18.moneyflow.data = try Data(contentsOf: fileURL)
                return Request.serializeResponseData(response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
            } catch {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileReadFailed(at: fileURL)))
            }
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter completionHandler: The code to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responseData(
        queue: DispatchQueue? = nil,
        completionHandler: @escaping (DownloadResponse<Data>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DownloadRequest.dataResponseSerializer(),
            completionHandler: completionHandler
        )
    }
}

// MARK: - String

extension Request {
    /// Returns a result string type initialized from the response com.prof18.moneyflow.data with the specified string encoding.
    ///
    /// - parameter encoding: The string encoding. If `nil`, the string encoding will be determined from the server
    ///                       response, falling back to the default HTTP default character set, ISO-8859-1.
    /// - parameter response: The response from the server.
    /// - parameter com.prof18.moneyflow.data:     The com.prof18.moneyflow.data returned from the server.
    /// - parameter error:    The error already encountered if it exists.
    ///
    /// - returns: The result com.prof18.moneyflow.data type.
    public static func serializeResponseString(
        encoding: String.Encoding?,
        response: HTTPURLResponse?,
        com.prof18.moneyflow.data: Data?,
        error: Error?)
        -> Result<String>
    {
        guard error == nil else { return .failure(error!) }

        if let response = response, emptyDataStatusCodes.contains(response.statusCode) { return .success("") }

        guard let validData = com.prof18.moneyflow.data else {
            return .failure(AFError.responseSerializationFailed(reason: .inputDataNil))
        }

        var convertedEncoding = encoding

        if let encodingName = response?.textEncodingName as CFString?, convertedEncoding == nil {
            convertedEncoding = String.Encoding(rawValue: CFStringConvertEncodingToNSStringEncoding(
                CFStringConvertIANACharSetNameToEncoding(encodingName))
            )
        }

        let actualEncoding = convertedEncoding ?? .isoLatin1

        if let string = String(com.prof18.moneyflow.data: validData, encoding: actualEncoding) {
            return .success(string)
        } else {
            return .failure(AFError.responseSerializationFailed(reason: .stringSerializationFailed(encoding: actualEncoding)))
        }
    }
}

extension DataRequest {
    /// Creates a response serializer that returns a result string type initialized from the response com.prof18.moneyflow.data with
    /// the specified string encoding.
    ///
    /// - parameter encoding: The string encoding. If `nil`, the string encoding will be determined from the server
    ///                       response, falling back to the default HTTP default character set, ISO-8859-1.
    ///
    /// - returns: A string response serializer.
    public static func stringResponseSerializer(encoding: String.Encoding? = nil) -> DataResponseSerializer<String> {
        return DataResponseSerializer { _, response, com.prof18.moneyflow.data, error in
            return Request.serializeResponseString(encoding: encoding, response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter encoding:          The string encoding. If `nil`, the string encoding will be determined from the
    ///                                server response, falling back to the default HTTP default character set,
    ///                                ISO-8859-1.
    /// - parameter completionHandler: A closure to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responseString(
        queue: DispatchQueue? = nil,
        encoding: String.Encoding? = nil,
        completionHandler: @escaping (DataResponse<String>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DataRequest.stringResponseSerializer(encoding: encoding),
            completionHandler: completionHandler
        )
    }
}

extension DownloadRequest {
    /// Creates a response serializer that returns a result string type initialized from the response com.prof18.moneyflow.data with
    /// the specified string encoding.
    ///
    /// - parameter encoding: The string encoding. If `nil`, the string encoding will be determined from the server
    ///                       response, falling back to the default HTTP default character set, ISO-8859-1.
    ///
    /// - returns: A string response serializer.
    public static func stringResponseSerializer(encoding: String.Encoding? = nil) -> DownloadResponseSerializer<String> {
        return DownloadResponseSerializer { _, response, fileURL, error in
            guard error == nil else { return .failure(error!) }

            guard let fileURL = fileURL else {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileNil))
            }

            do {
                let com.prof18.moneyflow.data = try Data(contentsOf: fileURL)
                return Request.serializeResponseString(encoding: encoding, response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
            } catch {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileReadFailed(at: fileURL)))
            }
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter encoding:          The string encoding. If `nil`, the string encoding will be determined from the
    ///                                server response, falling back to the default HTTP default character set,
    ///                                ISO-8859-1.
    /// - parameter completionHandler: A closure to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responseString(
        queue: DispatchQueue? = nil,
        encoding: String.Encoding? = nil,
        completionHandler: @escaping (DownloadResponse<String>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DownloadRequest.stringResponseSerializer(encoding: encoding),
            completionHandler: completionHandler
        )
    }
}

// MARK: - JSON

extension Request {
    /// Returns a JSON object contained in a result type constructed from the response com.prof18.moneyflow.data using `JSONSerialization`
    /// with the specified reading options.
    ///
    /// - parameter options:  The JSON serialization reading options. Defaults to `.allowFragments`.
    /// - parameter response: The response from the server.
    /// - parameter com.prof18.moneyflow.data:     The com.prof18.moneyflow.data returned from the server.
    /// - parameter error:    The error already encountered if it exists.
    ///
    /// - returns: The result com.prof18.moneyflow.data type.
    public static func serializeResponseJSON(
        options: JSONSerialization.ReadingOptions,
        response: HTTPURLResponse?,
        com.prof18.moneyflow.data: Data?,
        error: Error?)
        -> Result<Any>
    {
        guard error == nil else { return .failure(error!) }

        if let response = response, emptyDataStatusCodes.contains(response.statusCode) { return .success(NSNull()) }

        guard let validData = com.prof18.moneyflow.data, validData.count > 0 else {
            return .failure(AFError.responseSerializationFailed(reason: .inputDataNilOrZeroLength))
        }

        do {
            let json = try JSONSerialization.jsonObject(with: validData, options: options)
            return .success(json)
        } catch {
            return .failure(AFError.responseSerializationFailed(reason: .jsonSerializationFailed(error: error)))
        }
    }
}

extension DataRequest {
    /// Creates a response serializer that returns a JSON object result type constructed from the response com.prof18.moneyflow.data using
    /// `JSONSerialization` with the specified reading options.
    ///
    /// - parameter options: The JSON serialization reading options. Defaults to `.allowFragments`.
    ///
    /// - returns: A JSON object response serializer.
    public static func jsonResponseSerializer(
        options: JSONSerialization.ReadingOptions = .allowFragments)
        -> DataResponseSerializer<Any>
    {
        return DataResponseSerializer { _, response, com.prof18.moneyflow.data, error in
            return Request.serializeResponseJSON(options: options, response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter options:           The JSON serialization reading options. Defaults to `.allowFragments`.
    /// - parameter completionHandler: A closure to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responseJSON(
        queue: DispatchQueue? = nil,
        options: JSONSerialization.ReadingOptions = .allowFragments,
        completionHandler: @escaping (DataResponse<Any>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DataRequest.jsonResponseSerializer(options: options),
            completionHandler: completionHandler
        )
    }
}

extension DownloadRequest {
    /// Creates a response serializer that returns a JSON object result type constructed from the response com.prof18.moneyflow.data using
    /// `JSONSerialization` with the specified reading options.
    ///
    /// - parameter options: The JSON serialization reading options. Defaults to `.allowFragments`.
    ///
    /// - returns: A JSON object response serializer.
    public static func jsonResponseSerializer(
        options: JSONSerialization.ReadingOptions = .allowFragments)
        -> DownloadResponseSerializer<Any>
    {
        return DownloadResponseSerializer { _, response, fileURL, error in
            guard error == nil else { return .failure(error!) }

            guard let fileURL = fileURL else {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileNil))
            }

            do {
                let com.prof18.moneyflow.data = try Data(contentsOf: fileURL)
                return Request.serializeResponseJSON(options: options, response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
            } catch {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileReadFailed(at: fileURL)))
            }
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter options:           The JSON serialization reading options. Defaults to `.allowFragments`.
    /// - parameter completionHandler: A closure to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responseJSON(
        queue: DispatchQueue? = nil,
        options: JSONSerialization.ReadingOptions = .allowFragments,
        completionHandler: @escaping (DownloadResponse<Any>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DownloadRequest.jsonResponseSerializer(options: options),
            completionHandler: completionHandler
        )
    }
}

// MARK: - Property List

extension Request {
    /// Returns a plist object contained in a result type constructed from the response com.prof18.moneyflow.data using
    /// `PropertyListSerialization` with the specified reading options.
    ///
    /// - parameter options:  The property list reading options. Defaults to `[]`.
    /// - parameter response: The response from the server.
    /// - parameter com.prof18.moneyflow.data:     The com.prof18.moneyflow.data returned from the server.
    /// - parameter error:    The error already encountered if it exists.
    ///
    /// - returns: The result com.prof18.moneyflow.data type.
    public static func serializeResponsePropertyList(
        options: PropertyListSerialization.ReadOptions,
        response: HTTPURLResponse?,
        com.prof18.moneyflow.data: Data?,
        error: Error?)
        -> Result<Any>
    {
        guard error == nil else { return .failure(error!) }

        if let response = response, emptyDataStatusCodes.contains(response.statusCode) { return .success(NSNull()) }

        guard let validData = com.prof18.moneyflow.data, validData.count > 0 else {
            return .failure(AFError.responseSerializationFailed(reason: .inputDataNilOrZeroLength))
        }

        do {
            let plist = try PropertyListSerialization.propertyList(from: validData, options: options, format: nil)
            return .success(plist)
        } catch {
            return .failure(AFError.responseSerializationFailed(reason: .propertyListSerializationFailed(error: error)))
        }
    }
}

extension DataRequest {
    /// Creates a response serializer that returns an object constructed from the response com.prof18.moneyflow.data using
    /// `PropertyListSerialization` with the specified reading options.
    ///
    /// - parameter options: The property list reading options. Defaults to `[]`.
    ///
    /// - returns: A property list object response serializer.
    public static func propertyListResponseSerializer(
        options: PropertyListSerialization.ReadOptions = [])
        -> DataResponseSerializer<Any>
    {
        return DataResponseSerializer { _, response, com.prof18.moneyflow.data, error in
            return Request.serializeResponsePropertyList(options: options, response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter options:           The property list reading options. Defaults to `[]`.
    /// - parameter completionHandler: A closure to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responsePropertyList(
        queue: DispatchQueue? = nil,
        options: PropertyListSerialization.ReadOptions = [],
        completionHandler: @escaping (DataResponse<Any>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DataRequest.propertyListResponseSerializer(options: options),
            completionHandler: completionHandler
        )
    }
}

extension DownloadRequest {
    /// Creates a response serializer that returns an object constructed from the response com.prof18.moneyflow.data using
    /// `PropertyListSerialization` with the specified reading options.
    ///
    /// - parameter options: The property list reading options. Defaults to `[]`.
    ///
    /// - returns: A property list object response serializer.
    public static func propertyListResponseSerializer(
        options: PropertyListSerialization.ReadOptions = [])
        -> DownloadResponseSerializer<Any>
    {
        return DownloadResponseSerializer { _, response, fileURL, error in
            guard error == nil else { return .failure(error!) }

            guard let fileURL = fileURL else {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileNil))
            }

            do {
                let com.prof18.moneyflow.data = try Data(contentsOf: fileURL)
                return Request.serializeResponsePropertyList(options: options, response: response, com.prof18.moneyflow.data: com.prof18.moneyflow.data, error: error)
            } catch {
                return .failure(AFError.responseSerializationFailed(reason: .inputFileReadFailed(at: fileURL)))
            }
        }
    }

    /// Adds a handler to be called once the request has finished.
    ///
    /// - parameter options:           The property list reading options. Defaults to `[]`.
    /// - parameter completionHandler: A closure to be executed once the request has finished.
    ///
    /// - returns: The request.
    @discardableResult
    public func responsePropertyList(
        queue: DispatchQueue? = nil,
        options: PropertyListSerialization.ReadOptions = [],
        completionHandler: @escaping (DownloadResponse<Any>) -> Void)
        -> Self
    {
        return response(
            queue: queue,
            responseSerializer: DownloadRequest.propertyListResponseSerializer(options: options),
            completionHandler: completionHandler
        )
    }
}

/// A set of HTTP response status code that do not contain response com.prof18.moneyflow.data.
private let emptyDataStatusCodes: Set<Int> = [204, 205]
