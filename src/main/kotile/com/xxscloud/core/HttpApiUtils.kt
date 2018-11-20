package com.xxscloud.core

import com.squareup.okhttp.OkHttpClient
import com.xxscloud.core.data.*
import com.xxscloud.core.http.BaseHttpUtils
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.HashMap



open class HttpApiUtils() : BaseHttpUtils() {

    private val httpClient = OkHttpClient()

    constructor(ip: String, port: Int) : this() {
        httpClient.proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(ip, port))
    }

    override fun post(url: String, httpFormEntity: HttpFormEntity?): HttpResponseEntity {
        return this.post(url, httpFormEntity, null, 0)
    }

    override fun post(url: String, httpFormEntity: HttpFormEntity?, interval: Long?): HttpResponseEntity {
        return this.post(url, httpFormEntity, null, interval)
    }

    override fun post(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.post(url, httpFormEntity, httpHeaderEntity, null)
    }

    override fun post(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_FORM_DATA
        httpRequestEntity.parameters = httpFormEntity

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.POST, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun post(url: String, httpJsonEntity: HttpJsonEntity?): HttpResponseEntity {
        return this.post(url, httpJsonEntity, null, null)
    }

    override fun post(url: String, httpJsonEntity: HttpJsonEntity?, interval: Long?): HttpResponseEntity {
        return this.post(url, httpJsonEntity, null, interval)
    }

    override fun post(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.post(url, httpJsonEntity, httpHeaderEntity, null)
    }

    override fun post(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_JSON_CHARSET_UTF_8
        httpRequestEntity.bodyString = httpJsonEntity?.getData()

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.POST, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }


    override fun post(url: String, httpStreamEntity: HttpStreamEntity?): HttpResponseEntity {
        return this.post(url, httpStreamEntity, null, null)
    }

    override fun post(url: String, httpStreamEntity: HttpStreamEntity?, interval: Long?): HttpResponseEntity {
        return this.post(url, httpStreamEntity, null, null)
    }

    override fun post(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.post(url, httpStreamEntity, null, null)
    }

    override fun post(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_OCTET_STREAM
        httpRequestEntity.body = httpStreamEntity?.getData()

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.POST, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun put(url: String, httpFormEntity: HttpFormEntity?): HttpResponseEntity {
        return this.put(url, httpFormEntity, null, null)
    }

    override fun put(url: String, httpFormEntity: HttpFormEntity?, interval: Long?): HttpResponseEntity {
        return this.put(url, httpFormEntity, null, interval)
    }

    override fun put(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.put(url, httpFormEntity, httpHeaderEntity, null)
    }

    override fun put(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_FORM_DATA
        httpRequestEntity.parameters = httpFormEntity

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.PUT, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun put(url: String, httpJsonEntity: HttpJsonEntity?): HttpResponseEntity {
        return this.put(url, httpJsonEntity, null, null)
    }

    override fun put(url: String, httpJsonEntity: HttpJsonEntity?, interval: Long?): HttpResponseEntity {
        return this.put(url, httpJsonEntity, null, interval)
    }

    override fun put(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.put(url, httpJsonEntity, httpHeaderEntity, null)
    }

    override fun put(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_JSON_CHARSET_UTF_8
        httpRequestEntity.bodyString = httpJsonEntity?.getData()

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.PUT, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun put(url: String, httpStreamEntity: HttpStreamEntity?): HttpResponseEntity {
        return this.put(url, httpStreamEntity, null, null)
    }

    override fun put(url: String, httpStreamEntity: HttpStreamEntity?, interval: Long?): HttpResponseEntity {
        return this.put(url, httpStreamEntity, null, interval)
    }

    override fun put(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.put(url, httpStreamEntity, httpHeaderEntity, null)
    }

    override fun put(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_OCTET_STREAM
        httpRequestEntity.body = httpStreamEntity?.getData()

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.PUT, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun delete(url: String, httpFormEntity: HttpFormEntity?): HttpResponseEntity {
        return this.delete(url, httpFormEntity, null, null)
    }

    override fun delete(url: String, httpFormEntity: HttpFormEntity?, interval: Long?): HttpResponseEntity {
        return this.delete(url, httpFormEntity, null, null)
    }

    override fun delete(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.delete(url, httpFormEntity, null, null)
    }

    override fun delete(url: String, httpFormEntity: HttpFormEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_FORM_DATA
        httpRequestEntity.parameters = httpFormEntity

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.DELETE, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun delete(url: String, httpJsonEntity: HttpJsonEntity?): HttpResponseEntity {
        return this.delete(url, httpJsonEntity, null, null)
    }

    override fun delete(url: String, httpJsonEntity: HttpJsonEntity?, interval: Long?): HttpResponseEntity {
        return this.delete(url, httpJsonEntity, null, interval)
    }

    override fun delete(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.delete(url, httpJsonEntity, httpHeaderEntity, null)
    }

    override fun delete(url: String, httpJsonEntity: HttpJsonEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_JSON_CHARSET_UTF_8
        httpRequestEntity.bodyString = httpJsonEntity?.getData()

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.DELETE, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun delete(url: String, httpStreamEntity: HttpStreamEntity?): HttpResponseEntity {
        return this.delete(url, httpStreamEntity, null, null)
    }

    override fun delete(url: String, httpStreamEntity: HttpStreamEntity?, interval: Long?): HttpResponseEntity {
        return this.delete(url, httpStreamEntity, null, interval)
    }

    override fun delete(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.delete(url, httpStreamEntity, httpHeaderEntity, null)
    }

    override fun delete(url: String, httpStreamEntity: HttpStreamEntity?, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity
        httpRequestEntity.contextType = HttpRequestEntity.ContextType.APPLICATION_OCTET_STREAM
        httpRequestEntity.body = httpStreamEntity?.getData()

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.DELETE, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun get(url: String): HttpResponseEntity {
        return this.get(url, null, null)
    }

    override fun get(url: String, interval: Long?): HttpResponseEntity {
        return this.get(url, null, interval)
    }

    override fun get(url: String, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        return this.get(url, httpHeaderEntity, null)
    }

    override fun get(url: String, httpHeaderEntity: HttpHeaderEntity?, interval: Long?): HttpResponseEntity {
        val httpRequestEntity = HttpRequestEntity()
        httpRequestEntity.url = url
        httpRequestEntity.headers = httpHeaderEntity

        while (true) {
            try {
                return base(httpRequestEntity, HttpMethodEnum.GET, httpClient)
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (interval != null && interval > 0) {
                    Thread.sleep(interval)
                } else {
                    throw HttpException(ex.message ?: "")
                }
            }
        }
    }

    override fun loadWebPage(url: String, httpHeaderEntity: HttpHeaderEntity?): HttpResponseEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
