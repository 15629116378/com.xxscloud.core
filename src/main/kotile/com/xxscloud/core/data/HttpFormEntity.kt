package com.xxscloud.core.data

class HttpFormEntity : HashMap<String, Any> {
    constructor(headers: HashMap<String, *>) {
        headers.iterator().forEach {
            if (it.value != null) {
                this[it.key] = it.value.toString()
            }
        }
    }
}