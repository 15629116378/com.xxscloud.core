package com.xxscloud.core

import com.squareup.okhttp.OkHttpClient
import com.xxscloud.core.data.HttpMethodEnum
import com.xxscloud.core.data.HttpRequestEntity
import com.xxscloud.core.data.HttpResponseEntity
import com.xxscloud.core.http.BaseHttpUtils
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.HashMap


/**
 * @author : 李小双 2018.1.1
 * 描述: HTTP API 的工具类库
 */
open class HttpApiUtils() : BaseHttpUtils() {
    private val httpClient = OkHttpClient()

    constructor(ip: String, port: Int) : this() {
        httpClient.proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(ip, port))
    }

    /**
     * 发送一个Get 请求 .
     *
     * @param url Url地址
     * @return 处理结果
     */
    override fun get(url: String): HttpResponseEntity {
        return this.get(url, java.util.HashMap())
    }

    /**
     * 发送一个Get 请求 .
     *
     * @param url Url地址
     * @param interval 毫秒数
     * @return 处理结果
     */
    override fun get(url: String, interval: Long): HttpResponseEntity {
        while (true){
            try {
                return this.get(url, java.util.HashMap())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            Thread.sleep(interval)
        }
    }


    /**
     * 发送一个Get 请求 .
     *
     * @param url     Url地址
     * @param headers 请求头部
     * @return 处理结果
     */
    override fun get(url: String, headers: HashMap<String, String>): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = headers
        return base(httpRequestEntity, HttpMethodEnum.GET, httpClient)
    }

    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    override fun post(url: String, parameters: HashMap<String, Any>): HttpResponseEntity {
        return post(url, parameters, HashMap())
    }

    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @param headers    请求头部
     * @return 处理结果
     */
    override fun post(url: String, parameters: HashMap<String, Any>, headers: HashMap<String, String>): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = headers
        httpRequestEntity.parameters = parameters
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_X_WWW_FORM_URLENCODED
        return base(httpRequestEntity, HttpMethodEnum.POST, httpClient)
    }

    /**
     * 发送一个POST 请求.
     *
     * @param url     url 地址
     * @param headers 请求头部
     * @param body    请求正文
     * @return 处理结果
     */
    override fun post(url: String, headers: HashMap<String, String>, body: ByteArray): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = headers
        httpRequestEntity.body = body
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_X_WWW_FORM_URLENCODED
        return base(httpRequestEntity, HttpMethodEnum.POST, httpClient)
    }


    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    override fun post(url: String, parameters: String, headers: HashMap<String, String>): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = headers
        httpRequestEntity.bodyString = parameters
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_JSON_CHARSET_UTF_8
        return base(httpRequestEntity, HttpMethodEnum.POST, httpClient)
    }

    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    override fun post(url: String, parameters: String): HttpResponseEntity {
        return post(url, parameters, HashMap())
    }


    /**
     * 发送一个Put 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    override fun put(url: String, parameters: HashMap<String, Any>): HttpResponseEntity {
        return put(url, parameters, HashMap())
    }

    /**
     * 发送一个Put 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @param headers    请求正文
     * @return 响应实体
     */
    override fun put(url: String, parameters: HashMap<String, Any>, headers: HashMap<String, String>): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = headers
        httpRequestEntity.parameters = parameters
        return base(httpRequestEntity, HttpMethodEnum.PUT, httpClient)
    }

    /**
     * 发送一个Put 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @param body       请求body
     * @return 响应实体
     */
    override fun put(url: String, headers: HashMap<String, String>, body: ByteArray): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = headers
        httpRequestEntity.body = body
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_OCTET_STREAM
        return base(httpRequestEntity, HttpMethodEnum.PUT, httpClient)
    }

    /**
     * 发送一个DELETE 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 响应实体
     */
    override fun delete(url: String, parameters: HashMap<String, Any>): HttpResponseEntity {
        return delete(url, parameters, HashMap())
    }

    /**
     * 发送一个DELETE 请求.
     *
     * @param url        请求地址
     * @param parameters 请求参数
     * @param headers    请求头部
     * @return 响应实体
     */
    override fun delete(url: String, parameters: HashMap<String, Any>, headers: HashMap<String, String>): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = headers
        httpRequestEntity.parameters = parameters
        return base(httpRequestEntity, HttpMethodEnum.DELETE, httpClient)
    }


}
