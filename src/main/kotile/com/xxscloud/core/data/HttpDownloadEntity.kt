package com.xxscloud.core.data

class HttpDownloadEntity {

    private var downloadPath: String = ""

    private var tempPath: String = ""

    constructor(downloadPath: String, tempPath: String) {
        this.downloadPath = downloadPath
        this.tempPath = tempPath
    }

    fun getDownloadPath(): String {
        return this.downloadPath
    }

    fun getTempPath(): String {
        return this.tempPath
    }


}