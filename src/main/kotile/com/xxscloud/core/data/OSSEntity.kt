package com.xxscloud.core.data

import java.util.*

data class OSSEntity(
        var id: Long? = null,
        val groupId: String? = null,
        val path: String? = null,
        var name: String? = null,
        var desc: String? = null,
        var data: String? = null,
        val size: Long? = null,
        val md5: String? = null,
        var type: String? = null,
        val addTime: Date? = null
)