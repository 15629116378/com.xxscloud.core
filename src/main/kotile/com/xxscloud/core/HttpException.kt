package com.xxscloud.core



class HttpException : RuntimeException {

    constructor(message: String) : super(message) {}

    constructor(e: Throwable) : super(e) {}

    constructor(message: String, e: Throwable) : super(message, e) {}
}
