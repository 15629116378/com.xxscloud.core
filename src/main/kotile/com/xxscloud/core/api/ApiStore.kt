package com.xxscloud.core.api


import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import java.util.HashMap

class ApiStore(private val applicationContext: ApplicationContext) {

    private val logger = LoggerFactory.getLogger(ApiStore::class.java)

    private val apiMap = HashMap<String, ApiRunnable>()

    private var apiInterceptorName = ""

    private val apiTypes = HashMap<String, String>()

    /**
     * 扫描APIMapping 文件类.
     */
    fun loadApiFromSpringBeans() {
        logger.info("开始扫描 API beans")
        val names = applicationContext.beanDefinitionNames
        var type: Class<*>
        for (name in names) {
            type = applicationContext.getType(name)!!
            for (m in type.declaredMethods) {
                //是否被APIMapping 注解
                val apiMapping = m.getAnnotation(APIMapping::class.java)
                if (apiMapping != null) {
                    //添加到Map
                    val apiRunnable = ApiRunnable()
                    apiRunnable.name = apiMapping.value
                    apiRunnable.targetMethod = m
                    apiRunnable.targetName = name
                    apiRunnable.apiMapping = apiMapping
                    apiMap[apiMapping.value + "_" + apiMapping.version] = apiRunnable
                }
            }


            type.interfaces.forEach {
                //是否实现了接口 ApiInterceptor
                if (it.name == ApiInterceptor::class.java.name) {
                    this.apiInterceptorName = name
                }
                //是否实现了接口 ApiType
                if (it.name == ApiType::class.java.name) {
                    apiTypes[type.name] = name
                }
            }

        }
        logger.info("扫描完成 API  ${apiMap.size} , InterceptorName:$apiInterceptorName, Type:${apiTypes.size}")
    }

    /**
     * 查找对应的APIMapping 的反射类
     *
     * @param name 名称
     * @return 反射类
     */
    fun findApiRunnable(name: String, version: String): ApiRunnable? {
        var value: ApiRunnable? = apiMap[name + "_" + version]
        if (value == null) {
            value = apiMap[name + "_"]
        }
        return value
    }

    /**
     * 获得自定义类型的类.
     */
    fun findApiType(typeName: String?): ApiType<*>? {
        if (typeName == null || typeName.isEmpty()) {
            return null
        }
        val name = apiTypes[typeName] ?: return null
        return applicationContext.getBean(name) as ApiType<*>
    }

    /**
     * 获得接口实现类.
     */
    fun getInterceptor(): ApiInterceptor? {
        return if (apiInterceptorName.isEmpty()) null else applicationContext.getBean(apiInterceptorName) as ApiInterceptor
    }


}