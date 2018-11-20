package com.xxscloud.core

import com.xxscloud.core.domain.BaseDomainAggregation
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class SpringContextUtils : ApplicationContextAware {

    companion object {
        var applicationContext: ApplicationContext? = null
        fun <T> getBean(clazz: Class<T>): T? {
            return if (SpringContextUtils.applicationContext != null) {
                SpringContextUtils.applicationContext!!.getBean(clazz)
            } else null
        }
    }


    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextUtils.applicationContext = applicationContext
    }

}