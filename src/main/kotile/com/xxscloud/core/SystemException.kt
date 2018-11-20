package com.xxscloud.core

/**
 * @author 李小双 2018.1.1
 */
class SystemException : RuntimeException {

    constructor(message: String) : super(message) {}

    constructor(e: Throwable) : super(e) {}

    constructor(message: String, e: Throwable) : super(message, e) {}
}
