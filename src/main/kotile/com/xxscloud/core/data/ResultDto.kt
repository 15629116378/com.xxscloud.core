package com.xxscloud.core.data

import java.util.*
import javax.validation.constraints.NotNull


/**
 * 创建人: 李小双.
 * 创建时间:  2017/8/16.
 * 描述:
 */
data class ResultDto(
        var code: ResultEnum = ResultEnum.ERROR,
        var data: Any? = null,
        var message: String? = null,
        var timestamp: Long = Date().time,
        var url: String? = null,
        private var success: Boolean = code != ResultEnum.ERROR
) {
    constructor(code: ResultEnum, @NotNull data: Any?, message: String) : this() {
        this.code = code
        this.data = data
        this.message = message
        this.success = code != ResultEnum.ERROR
        this.timestamp = Date().time
    }
}

