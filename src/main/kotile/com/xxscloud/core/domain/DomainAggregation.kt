package com.xxscloud.core.domain

import java.io.Serializable


/**
 * @author 李小双 2018.1.10
 * 领域驱动的聚合根
 */
interface DomainAggregation<Root : Serializable> : DomainObject<Root> {
    //TODO 领域事件触发器

    var root: Root
}