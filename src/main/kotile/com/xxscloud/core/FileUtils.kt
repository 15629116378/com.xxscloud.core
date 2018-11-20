package com.xxscloud.core

import java.nio.file.Path
import java.nio.file.Paths

object FileUtils {

    fun getDefaultPath(): String {
        return System.getProperty("catalina.home")
    }

    fun readAll(path: Path): ByteArray? {
        try {
            val file = path.toFile()
            if (!file.exists()) {
                return null
            }
            return file.readBytes()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
    }

    fun readAll(path: String): ByteArray? {
        return readAll(Paths.get(path))
    }

    fun writeAll(path: Path, byteArray: ByteArray): Boolean {
        return try {
            val uri = path.toFile().toURI().toString()
            val index = uri.lastIndexOf("/")
            val directoryFile = Paths.get(uri.substring(6, index)).toFile()
            val file = path.toFile()
            if (!file.exists()) {
                directoryFile.mkdirs()
                file.createNewFile()
            }
            file.writeBytes(byteArray)
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun writeAll(path: String, byteArray: ByteArray): Boolean {
        return writeAll(Paths.get(path), byteArray)
    }
}