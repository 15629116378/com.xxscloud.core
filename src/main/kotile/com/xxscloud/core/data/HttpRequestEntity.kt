package com.xxscloud.core.data


import java.io.Serializable
import kotlin.collections.HashMap


/**
 * @author 李小双 2018.1.1
 */
class HttpRequestEntity : Serializable {

    /**
     * 地址.
     */
    var url: String? = null


    /**
     * 请求头部
     */
    var headers: HttpHeaderEntity? = null


    /**
     * 请求正文.
     */
    var body: ByteArray? = null

    /**
     * 请求参数.
     */
    var parameters: HttpFormEntity? = null

    /**
     * 请求字符串.
     */
    var bodyString: String? = null


    /**
     * Cookie.

    var cookieStore: CookieStore? = null

     */
    /**
     * 超时时间, 单位毫秒.
     */
    var time: Long = 0

    /**
     * 正文内容.
     */
    var contextType: ContextType? = null

    /**
     * 构造器初始化.
     */
    init {
        //默认3000 毫秒
        this.time = 3000
    }


    enum class ContextType {
        APPLICATION_FORM_DATA,
        APPLICATION_X_WWW_FORM_URLENCODED,
        APPLICATION_OCTET_STREAM,
        APPLICATION_JSON_CHARSET_UTF_8,
        APPLICATION_NONE,
        APPLICATION_JAVASCRIPT,
        TEXT_PLAIN,
        APPLICATION_XML,
        TEXT_XML,
        TEXT_HTML
    }
}
