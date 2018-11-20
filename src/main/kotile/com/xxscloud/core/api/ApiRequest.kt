package com.xxscloud.core.api

open class ApiRequest {


    /**
     * 名称.
     */
    var name: String? = null

    /**
     * 令牌.
     */
    var token: String? = null

    /**
     * 签名.
     */
    var signture: String? = null

    /**
     * 设备用户标示号.
     */
    var uCode: String? = null

    /**
     * 设备标示号.
     */
    var eCode: String? = null

    /**
     * 时间.
     */
    var timestamps: Long = 0

}