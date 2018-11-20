package com.xxscloud.core.http


import com.xxscloud.core.data.HttpResponseEntity
import java.util.*


/**
 * HttpUtils 接口
 */
interface HttpUtils {

    /**
     * 发送一个Get 请求 .
     *
     * @param url Url地址
     * @return 处理结果
     */
    fun get(url: String): HttpResponseEntity


    /**
     * 发送一个Get 请求，直到成功为止 .
     *
     * @param url Url地址
     * @param interval 间隔
     * @return 处理结果
     */
    fun get(url: String, interval: Long): HttpResponseEntity

    /**
     * 发送一个Get 请求 .
     *
     * @param url     Url地址
     * @param headers 请求头部
     * @return 处理结果
     */
    fun get(url: String, headers: HashMap<String, String>): HttpResponseEntity

    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    fun post(url: String, parameters: HashMap<String, Any>): HttpResponseEntity

    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @param headers    请求头部
     * @return 处理结果
     */
    fun post(url: String, parameters: HashMap<String, Any>, headers: HashMap<String, String>): HttpResponseEntity

    /**
     * 发送一个POST 请求.
     *
     * @param url     url 地址
     * @param headers 请求头部
     * @param body    请求正文
     * @return 处理结果
     */
    fun post(url: String, headers: HashMap<String, String>, body: ByteArray): HttpResponseEntity

    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    fun post(url: String, parameters: String, headers: HashMap<String, String>): HttpResponseEntity

    /**
     * 发送一个POST 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    fun post(url: String, parameters: String): HttpResponseEntity

    /**
     * 发送一个Put 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 处理结果
     */
    fun put(url: String, parameters: HashMap<String, Any>): HttpResponseEntity

    /**
     * 发送一个Put 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @param headers    请求正文
     * @return 响应实体
     */
    fun put(url: String, parameters: HashMap<String, Any>, headers: HashMap<String, String>): HttpResponseEntity

    /**
     * 发送一个Put 请求.
     *
     * @param url        url 地址
     * @param headers 请求头部
     * @param body       请求body
     * @return 响应实体
     */
    fun put(url: String, headers: HashMap<String, String>, body: ByteArray): HttpResponseEntity

    /**
     * 发送一个DELETE 请求.
     *
     * @param url        url 地址
     * @param parameters 请求参数
     * @return 响应实体
     */
    fun delete(url: String, parameters: HashMap<String, Any>): HttpResponseEntity

    /**
     * 发送一个DELETE 请求.
     *
     * @param url        请求地址
     * @param parameters 请求参数
     * @param headers    请求头部
     * @return 响应实体
     */
    fun delete(url: String, parameters: HashMap<String, Any>, headers: HashMap<String, String>): HttpResponseEntity
}

