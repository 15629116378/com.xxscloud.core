package com.xxscloud.core.api

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.xxscloud.core.JsonUtils
import com.xxscloud.core.StringUtils
import com.xxscloud.core.data.ResultDto
import com.xxscloud.core.data.ResultEnum
import eu.bitwalker.useragentutils.UserAgent
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.LocalVariableTableParameterNameDiscoverer
import org.springframework.core.ParameterNameDiscoverer
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.reflect.Method
import java.io.InputStreamReader
import java.io.BufferedReader


open class ApiCoreController : InitializingBean, ApplicationContextAware {

    private lateinit var apiStore: ApiStore
    private val parameterNameDiscoverer: ParameterNameDiscoverer = LocalVariableTableParameterNameDiscoverer()
    private lateinit var applicationContext: ApplicationContext


    companion object {
        private const val METHOD = "method"
        private const val PARAMETER = "parameter"
        private const val SIGNATURE = "signature"
        private const val TIMESTAMPS = "timestamps"
        private const val TOKEN = "token"
    }


    override fun afterPropertiesSet() {
        apiStore.loadApiFromSpringBeans()
    }

    override fun setApplicationContext(p0: ApplicationContext) {
        this.applicationContext = p0
        apiStore = ApiStore(p0)
    }


    /**
     * 处理API请求.
     */
    open fun apiHandle(request: HttpServletRequest, response: HttpServletResponse, version: String, channel: String): String {

        //获得客户端信息
        val clientInfo: ClientInfo = getClientInfo(request)

        //获得apiInterceptor 拦截器
        val apiInterceptor: ApiInterceptor? = apiStore.getInterceptor()

        //调用拦截器初始化方法
        apiInterceptor?.init(request, response)


        // 获得map 数据
        var map: JsonObject? = null
        try {

            val bufferedReader = BufferedReader(InputStreamReader(request.inputStream, "UTF-8"))
            var line: String?
            val jsonBuilder = StringBuilder()
            while (true) {
                line = bufferedReader.readLine()
                if (line == null) {
                    break
                }
                jsonBuilder.append(line)
            }

            val parameter: String? = request.getParameter(PARAMETER) ?: jsonBuilder.toString()
            if (parameter != null && parameter.trim().isNotEmpty()) {
                map = JsonUtils.parseObject(parameter)
            }
        } catch (ex: JsonSyntaxException) {
            return JsonUtils.stringify(ResultDto(ResultEnum.ERROR, message = " $PARAMETER NOT JSON"))
        }


        //获得需要执行的API 方法
        val method: String = request.getParameter(METHOD)
                ?: return JsonUtils.stringify(ResultDto(ResultEnum.ERROR, message = " $METHOD IS NULL"))
        System.err.print(method)
        val apiRunnable: ApiRunnable = getMethod(method.trim(), version)
                ?: return JsonUtils.stringify(ResultDto(ResultEnum.ERROR, message = " $METHOD Can't find"))


        //获得ApiRequest 对象
        val apiRequest: ApiRequest
        try {
            apiRequest = getApiRequest(request)
        } catch (ex: ApiException) {
            return JsonUtils.stringify(ResultDto(ResultEnum.ERROR, message = ex.message))
        }


        val result: Any?
        try {
            //请求前拦截器
            if (apiInterceptor != null) {
                if (!apiInterceptor.preHandle(request, response, clientInfo, map, apiRunnable.apiMapping)) {
                    return JsonUtils.stringify(ResultDto(ResultEnum.ERROR, null, "非法请求"))
                }
            }

            //获得方法参数
            val args: Array<Any?> = buildParams(apiRunnable, request, response, apiRequest, clientInfo, map)

            //执行方法
            val startExecutionTime: Long = System.currentTimeMillis()
            result = apiRunnable.run(applicationContext, args)

            //响应前执行的注解
            apiInterceptor?.afterCompletion(request, response, clientInfo, map, apiRunnable.apiMapping,
                    System.currentTimeMillis() - startExecutionTime, result)

        } catch (ex: ApiException) {
            ex.printStackTrace()
            val message: String = apiInterceptor?.error(request, response, clientInfo, map, apiRunnable.apiMapping, ex)
                    ?: ex.message ?: ""
            return JsonUtils.stringify(ResultDto(ResultEnum.ERROR, message = message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            val message: String = apiInterceptor?.error(request, response, clientInfo, map, apiRunnable.apiMapping, ex)
                    ?: "系统异常"
            return JsonUtils.stringify(ResultDto(ResultEnum.ERROR, message = message))
        }


        //根据响应类型响应不同结果
        return when (result) {
            null -> {
                JsonUtils.stringify(ResultDto(ResultEnum.ERROR, result))
            }
            is ResultDto -> {
                JsonUtils.stringify(result)
            }
            else -> {
                JsonUtils.stringify(ResultDto(ResultEnum.SUCCEED, result))
            }
        }

    }


    /**
     * 创建一个参数.
     *
     * @param apiRunnable 反射对象
     * @param request     请求实体
     * @param response    响应实体
     * @param apiRequest  客户端信息
     * @param clientInfo  API请求实体
     * @return 反射对象.
     */
    private fun buildParams(apiRunnable: ApiRunnable, request: HttpServletRequest, response: HttpServletResponse,
                            apiRequest: ApiRequest, clientInfo: ClientInfo, map: JsonObject?): Array<Any?> {
        //获得方法的反射
        val method: Method = apiRunnable.targetMethod ?: throw ApiException("targetMethod is null error")
        //获得方法上参数的反射
        val paramNames = Arrays.asList<String>(*parameterNameDiscoverer.getParameterNames(method))
        //获得方法参数类型的反射
        val paramTypes = method.parameterTypes
        //获得方法参数的类型的注解
        val annotations = method.parameterAnnotations

        //默认不允许NULL , 判断参数是否允许NULL
        val paramNulls: Array<Boolean?> = arrayOfNulls(paramTypes.size)
        for ((index, value) in paramNames.withIndex()) {
            paramNulls[index] = false
            // 获得是否拥有注解标识
            for (item in annotations[index]) {
                if (item.annotationClass == APINullable::class) {
                    paramNulls[index] = true
                    break
                }
            }
        }

        //如果参数低于必须参数就错误
        val paramCount = map?.size() ?: 0
        if (paramNulls.filter { it == false }.size > paramCount) {
            throw ApiException("参数缺失", "9999")
        }

        //设置参数
        if (paramTypes.isNotEmpty() && map != null) {
            //注入参数
            val args = arrayOfNulls<Any>(paramTypes.size)
            for (i in paramTypes.indices) {
                when {
                    paramTypes[i].isAssignableFrom(HttpServletRequest::class.java) -> args[i] = request
                    paramTypes[i].isAssignableFrom(HttpServletResponse::class.java) -> args[i] = response
                    paramTypes[i].isAssignableFrom(ApiRequest::class.java) -> args[i] = apiRequest
                    paramTypes[i].isAssignableFrom(ClientInfo::class.java) -> args[i] = clientInfo
                    map.entrySet().findLast { it.key == paramNames[i] } != null -> {
                        args[i] = JsonUtils.parseObject(java.net.URLDecoder.decode(JsonUtils.stringify(map[paramNames[i]]), "UTF-8"), paramTypes[i])
                    }
                    else -> {
                        val customizeType = apiStore.findApiType(paramTypes[i].name)
                        when {
                            customizeType != null -> {
                                args[i] = customizeType.getType(map)
                            }
                            paramNulls[i] == true -> args[i] = null
                            else -> throw ApiException("参数 ${paramNames[i]} 错误", "9999")
                        }
                    }
                }
            }
            return args
        }
        return arrayOfNulls(0)
    }

    /**
     * 获得客户端信息.
     */
    private fun getClientInfo(request: HttpServletRequest): ClientInfo {
        //获取IP
        var ip = request.getHeader("x-forwarded-for")
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        ip = if (ip!!.contains(",")) {
            ip.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            ip
        }

        //获得操作系统数据
        val agentString: String? = request.getHeader("User-Agent")
        return if (agentString == null || agentString.isEmpty()) {
            ClientInfo(ip = ip, system = "", browserName = "", browserVersion = "")
        } else {
            val userAgent: UserAgent = UserAgent.parseUserAgentString(agentString)
            return ClientInfo(ip = ip, system = userAgent.operatingSystem.getName(),
                    browserName = userAgent.browser.getName(), browserVersion = userAgent.browserVersion.version)
        }
    }

    /**
     * 获得方法.
     */
    private fun getMethod(name: String, version: String): ApiRunnable? {
        return apiStore.findApiRunnable(name, version)
    }

    /**
     * 获得ApiRequest 实体.
     */
    private fun getApiRequest(request: HttpServletRequest): ApiRequest {
        val timestampsString: String? = request.getParameter(TIMESTAMPS)
        if (timestampsString == null || timestampsString.isEmpty() || !StringUtils.isLong(timestampsString)) {
            throw ApiException("timestamps is error", "9999")
        }
        val timestamps: Long = timestampsString.toLong()
        val apiRequest = ApiRequest()
        apiRequest.token = request.getParameter(TOKEN)
        apiRequest.signture = request.getParameter(SIGNATURE)
        apiRequest.timestamps = timestamps
        apiRequest.eCode = request.getParameter("eCode")
        apiRequest.uCode = request.getParameter("uCode")
        return apiRequest
    }

}