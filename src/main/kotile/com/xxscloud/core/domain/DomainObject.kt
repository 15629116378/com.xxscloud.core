package com.xxscloud.core.domain

import java.io.Serializable

/**
 * @author 李小双 2018.1.10
 * 领域对象基类
 */
interface DomainObject<Root : Serializable> : Serializable