package com.xxscloud.core.api

import org.springframework.context.ApplicationContext
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


/**
 * @author 李小双 2018.2.22
 * API .
 */
class ApiRunnable {


    var name: String? = null

    var targetName: String? = null

    var target: Any? = null

    var targetMethod: Method? = null


    var apiMapping: APIMapping? = null


    val paramTypes: Array<Class<*>>? = null

    /**
     * 执行方法.
     *
     * @param applicationContext 上下文
     * @param args               多个参数
     * @return 执行结果
     * @throws IllegalAccessException    访问异常
     * @throws IllegalArgumentException  参数异常
     * @throws InvocationTargetException 调用目标异常
     */
    fun run(applicationContext: ApplicationContext, args: Array<Any?>): Any {
        if (target == null) {
            target = applicationContext.getBean(targetName!!)
        }

        if (args.isNotEmpty()) {
            return targetMethod!!.invoke(target, *args)
        }
        return targetMethod!!.invoke(target)
    }
}
