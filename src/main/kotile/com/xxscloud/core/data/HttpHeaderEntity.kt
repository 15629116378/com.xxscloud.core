package com.xxscloud.core.data

class HttpHeaderEntity : HashMap<String, String?> {
    constructor(headers: HashMap<String, *>) {
        headers.iterator().forEach {
            if (it.value != null) {
                this[it.key] = it.value.toString()
            }
        }
    }
}