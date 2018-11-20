package com.xxscloud.core.api

import com.google.gson.JsonObject
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


interface ApiInterceptor {


    fun init(request: HttpServletRequest, response: HttpServletResponse)

    fun preHandle(request: HttpServletRequest, response: HttpServletResponse, clientInfo: ClientInfo,
                  j: JsonObject?, apiMapping: APIMapping?): Boolean

    fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, clientInfo: ClientInfo,
                        j: JsonObject?, apiMapping: APIMapping?, executionTime: Long, result: Any)

    fun error(request: HttpServletRequest, response: HttpServletResponse, clientInfo: ClientInfo,
              j: JsonObject?, apiMapping: APIMapping?, exception: Exception): String

}