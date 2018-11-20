package com.xxscloud.core.api



@Target(AnnotationTarget.FUNCTION)
annotation class APIMapping(
        val value: String, val login: Boolean = false,
        val version: String = "", val channel: String = "",
        val type: String = "", val tag: String = "")
