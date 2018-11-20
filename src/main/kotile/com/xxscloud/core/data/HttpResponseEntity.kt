package com.xxscloud.core.data


import com.squareup.okhttp.Headers
import java.io.UnsupportedEncodingException
import java.util.*


/**
 * @author 李小双 2018.1.1
 */
class HttpResponseEntity {


    /**
     * 是否执行成功.
     */
    var succeed: Boolean = false

    /**
     * 响应代码.
     */
    var code: ResponseEnum? = null

    /**
     * 消息状态.
     */
    var message: String? = null

    /**
     * 响应正文.
     */
    var body: ByteArray? = null

    /**
     * 响应头部.
     */
    var headers: Headers? = null



    private var _bodyString: String = ""
    private var _bodyStringCharset: String = ""
    /**
     * 获得body 值.
     *
     * @return body 值.
     */
    val bodyString: String?
        get() {
            try {
                return getBodyString("UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            return null
        }

    constructor(succeed: Boolean, responseEnum: ResponseEnum, message: String) {
        this.succeed = succeed
        this.code = responseEnum
        this.message = message
    }

    constructor(succeed: Boolean, responseEnum: ResponseEnum, body: ByteArray,
                headers: Headers) {
        this.succeed = succeed
        this.code = responseEnum
        this.body = body
        this.headers = headers
    }


    /**
     * 获得body 值.
     *
     * @param charsetName 字符串编码.
     * @return body 值.
     * @throws UnsupportedEncodingException 不支持的编码异常.
     */
    fun getBodyString(charsetName: String): String? {
        return if (Objects.isNull(body)) {
            null
        } else {
            if (_bodyStringCharset == charsetName && _bodyString != "") {
                _bodyStringCharset
            } else {
                _bodyStringCharset = charsetName
                _bodyString = java.lang.String(body, charsetName) as String
                _bodyString
            }
        }
    }

}
