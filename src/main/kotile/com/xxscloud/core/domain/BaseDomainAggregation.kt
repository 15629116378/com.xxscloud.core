package com.xxscloud.core.domain


import com.xxscloud.core.SpringContextUtils
import org.springframework.aop.framework.AopContext
import org.springframework.context.*
import org.springframework.context.annotation.EnableAspectJAutoProxy
import java.io.Serializable


@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
abstract class BaseDomainAggregation<Root : Serializable> : ApplicationContextAware, ApplicationEventPublisherAware, DomainAggregation<Root> {


    companion object {
        var applicationContext: ApplicationContext? = null
        var applicationEventPublisher: ApplicationEventPublisher? = null
        fun <T> getBean(clazz: Class<T>): T? {
            return if (BaseDomainAggregation.applicationContext != null) {
                BaseDomainAggregation.applicationContext!!.getBean(clazz)
            } else  SpringContextUtils.getBean(clazz)
        }

        fun getThis(): Any {
            return AopContext.currentProxy()
        }

        fun publisher(applicationEvent: ApplicationEvent): Any? {
            return BaseDomainAggregation.applicationEventPublisher?.publishEvent(applicationEvent)
        }
    }


    override fun setApplicationContext(applicationContext: ApplicationContext) {
        BaseDomainAggregation.applicationContext = applicationContext
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj is BaseDomainAggregation<*>) {
            (obj as BaseDomainAggregation<*>).root == this.root
        } else false
    }


    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        BaseDomainAggregation.applicationEventPublisher = applicationEventPublisher
    }

}
