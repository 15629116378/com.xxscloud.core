package com.xxscloud.core.domain

import java.io.Serializable

/**
 * @param <Root>
 * @author 李小双 2018.1.10
 * 领域对象的值对象抽象类
 */
abstract class BaseDomainObject<Root : Serializable> : DomainObject<Root>
