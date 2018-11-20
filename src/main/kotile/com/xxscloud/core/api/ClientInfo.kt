package com.xxscloud.core.api



data class ClientInfo(
        val ip: String,
        val browserVersion: String,
        val browserName: String,
        val system: String
)