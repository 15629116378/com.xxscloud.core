package com.xxscloud.core.http


import com.squareup.okhttp.*
import com.xxscloud.core.data.HttpMethodEnum
import com.xxscloud.core.data.HttpRequestEntity
import com.xxscloud.core.data.HttpResponseEntity
import com.xxscloud.core.data.ResponseEnum
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.squareup.okhttp.RequestBody
import java.io.IOException


abstract class BaseHttpUtils : HttpUtils {
    private val log: Logger = LoggerFactory.getLogger(BaseHttpUtils::class.java)


    /**
     * 发送请求底层 .
     *
     */
    protected fun base(httpRequestEntity: HttpRequestEntity, httpMethodEnum: HttpMethodEnum,
                       httpClient: OkHttpClient): HttpResponseEntity {

        //校验参数
        if (httpRequestEntity.url == null || httpRequestEntity.url == "") {
            return HttpResponseEntity(false, ResponseEnum.ERROR, "url is empty")
        }

        val request = Request.Builder()
                .url(httpRequestEntity.url)


        //默认的请求头部
        var defaultHeader = true
        if (httpRequestEntity.headers != null) {
            httpRequestEntity.headers!!.forEach(action = {
                if (it.key.toLowerCase() == "user-agent") {
                    defaultHeader = false
                }
                if (it.key != "" && it.value != "") {
                    request.addHeader(it.key.toLowerCase(), it.value)
                }
            })
        }
        if (defaultHeader) {
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
        }


        //参数类型
        var body: RequestBody? = null
        when (httpRequestEntity.contextType) {
            HttpRequestEntity.ContextType.APPLICATION_FORM_DATA -> {
                var context = ""
                httpRequestEntity.parameters?.forEach {
                    context = context + it.key + "=" + it.value + "&"
                }
                body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), context)
            }
            HttpRequestEntity.ContextType.APPLICATION_X_WWW_FORM_URLENCODED -> {
                var context = ""
                httpRequestEntity.parameters?.forEach {
                    context = context + it.key + "=" + java.net.URLEncoder.encode(it.value.toString(), "UTF-8") + "&"
                }
                context = if (!context.isEmpty()) context.substring(0, context.length - 1) else context
                body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), context)
            }
            HttpRequestEntity.ContextType.APPLICATION_JSON_CHARSET_UTF_8 -> {
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), httpRequestEntity.bodyString)
            }
            HttpRequestEntity.ContextType.APPLICATION_OCTET_STREAM -> {
                body = RequestBody.create(MediaType.parse("application/octet-stream"), httpRequestEntity.body, 0, httpRequestEntity.body!!.size)
            }
            HttpRequestEntity.ContextType.APPLICATION_JAVASCRIPT -> {
                body = RequestBody.create(MediaType.parse("application/javascript; charset=utf-8"), httpRequestEntity.bodyString)
            }
            HttpRequestEntity.ContextType.APPLICATION_XML -> {
                body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), httpRequestEntity.bodyString)
            }
            HttpRequestEntity.ContextType.TEXT_HTML -> {
                body = RequestBody.create(MediaType.parse("text/html; charset=utf-8"), httpRequestEntity.bodyString)
            }
            HttpRequestEntity.ContextType.TEXT_PLAIN -> {
                body = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), httpRequestEntity.bodyString)
            }
            HttpRequestEntity.ContextType.TEXT_XML -> {
                body = RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), httpRequestEntity.bodyString)
            }
            HttpRequestEntity.ContextType.APPLICATION_NONE -> {
                body = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), httpRequestEntity.bodyString)
            }
        }


        //添加请求参数
        when (httpMethodEnum) {
            HttpMethodEnum.POST -> request.post(body)
            HttpMethodEnum.PUT -> request.put(body)
            HttpMethodEnum.DELETE -> request.delete(body)
            HttpMethodEnum.GET -> request.get()
        }

        log.debug("发送请求: " + httpRequestEntity.url)
        val sendTime = System.currentTimeMillis()
        val response: Response?

        response = try {
            httpClient.newCall(request.build()).execute()
        } catch (ex: IOException) {
            ex.printStackTrace()
            log.info("请求异常，尝试第二次")
            try {
                httpClient.newCall(request.build()).execute()
            } catch (ex: IOException) {
                ex.printStackTrace()
                log.info("请求异常，尝试第三次")
                httpClient.newCall(request.build()).execute()
            }
        }

        if (!response!!.isSuccessful) {
            return HttpResponseEntity(false, ResponseEnum.SUCCEED.setState(response.code()),
                    " service " + response.code())
        }

        //响应正文
        val consumingTime: Long = System.currentTimeMillis() - sendTime
        if (consumingTime > 30000) {
            log.warn(httpRequestEntity.url + "\t 耗时:" + consumingTime + " 毫秒")
        } else {
            log.debug(httpRequestEntity.url + "\t 耗时:" + consumingTime + " 毫秒")
        }
        return HttpResponseEntity(true, ResponseEnum.SUCCEED, response.body().bytes(), response.headers())
    }
}
