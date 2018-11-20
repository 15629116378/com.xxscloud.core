package com.xxscloud.core.data

import org.apache.poi.util.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


class HttpStreamEntity {
    private var data: ByteArray = ByteArray(0)

    constructor(data: ByteArray) {
        this.data = data
    }

    constructor(file: File) {
        val inputStream = FileInputStream(file)
        val byte = ByteArray(inputStream.available())
        IOUtils.readFully(inputStream, byte)
        this.data = byte
    }

    constructor(inputStream: InputStream) {
        val byte = ByteArray(inputStream.available())
        IOUtils.readFully(inputStream, byte)
        this.data = byte
    }

    fun getData(): ByteArray {
        return data
    }
}