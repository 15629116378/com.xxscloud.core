package com.xxscloud.core.domain

import com.xxscloud.core.SpringContextUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

/**
 * @author 李小双 2018.1.10
 * 领域对象,实体类的抽象接口
 */
abstract class BaseDomainEntity : DomainEntity, ApplicationContextAware {
    companion object {
        var applicationContext: ApplicationContext? = null

        fun <T> getBean(clazz: Class<T>): T? {
            return if (BaseDomainEntity.applicationContext != null) {
                BaseDomainEntity.applicationContext!!.getBean(clazz)
            } else SpringContextUtils.getBean(clazz)
        }

    }


    override fun setApplicationContext(applicationContext: ApplicationContext) {
        BaseDomainEntity.applicationContext = applicationContext
    }
}