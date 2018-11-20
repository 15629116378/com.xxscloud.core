package com.xxscloud.core.api

/**
 * 自定义异常.
 */
open class ApiException : RuntimeException {

    var code: String = ""


    constructor() {

    }

    constructor(message: kotlin.String, code: String) : super("Code: $code, $message") {
        this.code = code
    }

    constructor(message: kotlin.String) : super(message) {

    }

    constructor(message: kotlin.String, cause: kotlin.Throwable) : super(message, cause) {

    }

    constructor(cause: kotlin.Throwable) : super(cause) {

    }

    constructor(message: kotlin.String, cause: kotlin.Throwable, enableSuppression: kotlin.Boolean,
                writableStackTrace: kotlin.Boolean) : super(message, cause, enableSuppression, writableStackTrace) {

    }
}