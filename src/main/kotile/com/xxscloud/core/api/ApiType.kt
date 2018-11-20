package com.xxscloud.core.api

import com.google.gson.JsonObject

interface ApiType<T> {
    fun getType(j: JsonObject): T
}