package com.xxscloud.core.data

import com.xxscloud.core.JsonUtils

class HttpJsonEntity {
    private var json: String = ""

    constructor(json: String) {
        this.json = json
    }

    constructor(obj: Any) : this(JsonUtils.stringify(obj))

    fun getData(): String {
        return json
    }
}