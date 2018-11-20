package com.xxscloud.core.http


import com.xxscloud.core.data.*


interface HttpUtils : HttpInterceptor {

    fun get(url: String): HttpResponseEntity

    fun get(url: String, interval: Long?): HttpResponseEntity

    fun get(url: String, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun get(url: String, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun post(url: String, httpFormEntity: HttpFormEntity?): HttpResponseEntity

    fun post(url: String, httpFormEntity: HttpFormEntity?, interval: Long?): HttpResponseEntity

    fun post(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun post(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun post(url: String, httpJsonEntity: HttpJsonEntity?): HttpResponseEntity

    fun post(url: String, httpJsonEntity: HttpJsonEntity?, interval: Long?): HttpResponseEntity

    fun post(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun post(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun post(url: String, httpStreamEntity: HttpStreamEntity?): HttpResponseEntity

    fun post(url: String, httpStreamEntity: HttpStreamEntity?, interval: Long?): HttpResponseEntity

    fun post(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun post(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun put(url: String, httpFormEntity: HttpFormEntity?): HttpResponseEntity

    fun put(url: String, httpFormEntity: HttpFormEntity?, interval: Long?): HttpResponseEntity

    fun put(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun put(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun put(url: String, httpJsonEntity: HttpJsonEntity?): HttpResponseEntity

    fun put(url: String, httpJsonEntity: HttpJsonEntity?, interval: Long?): HttpResponseEntity

    fun put(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun put(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun put(url: String, httpStreamEntity: HttpStreamEntity?): HttpResponseEntity

    fun put(url: String, httpStreamEntity: HttpStreamEntity?, interval: Long?): HttpResponseEntity

    fun put(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun put(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun delete(url: String, httpFormEntity: HttpFormEntity?): HttpResponseEntity

    fun delete(url: String, httpFormEntity: HttpFormEntity?, interval: Long?): HttpResponseEntity

    fun delete(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun delete(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun delete(url: String, httpJsonEntity: HttpJsonEntity?): HttpResponseEntity

    fun delete(url: String, httpJsonEntity: HttpJsonEntity?, interval: Long?): HttpResponseEntity

    fun delete(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun delete(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun delete(url: String, httpStreamEntity: HttpStreamEntity?): HttpResponseEntity

    fun delete(url: String, httpStreamEntity: HttpStreamEntity?, interval: Long?): HttpResponseEntity

    fun delete(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

    fun delete(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity

    fun loadWebPage(url: String, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity

}

