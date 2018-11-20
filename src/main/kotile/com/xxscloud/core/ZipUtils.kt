package com.xxscloud.core

import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.util.Zip4jConstants
import java.io.*
import java.nio.file.Paths
import java.util.zip.GZIPOutputStream
import java.util.zip.ZipOutputStream
import java.io.FileOutputStream


object ZipUtils {
    fun toZip(byteBufferList: HashMap<String, ByteArray>, password: String): ByteArray {
        /**
        压缩方式
        COMP_STORE = 0;（仅打包，不压缩）
        COMP_DEFLATE = 8;（默认）
        COMP_AES_ENC = 99; 加密压缩

        压缩级别
        DEFLATE_LEVEL_FASTEST = 1; (速度最快，压缩比最小)
        DEFLATE_LEVEL_FAST = 3; (速度快，压缩比小)
        DEFLATE_LEVEL_NORMAL = 5; (一般)
        DEFLATE_LEVEL_MAXIMUM = 7;
        DEFLATE_LEVEL_ULTRA = 9;



         */

//        ZipOutputStream
        val zip = org.apache.tools.ant.taskdefs.Zip()
//        zip.c

        val f = Paths.get("D:\\T", Utils.createId() + ".zip")
        val file = f.toFile()
        file.createNewFile()
        val outStream = ZipOutputStream(FileOutputStream(f.toFile()))
        outStream.closeEntry()
        val zipFile = ZipFile(file)
//        zipFile.setPassword(password)
//        zipParameters.isEncryptFiles = true
//        zipParameters.encryptionMethod = Zip4jConstants.ENC_METHOD_STANDARD

        val zipParameters: ZipParameters = ZipParameters()
        zipParameters.compressionMethod = Zip4jConstants.COMP_DEFLATE
        zipParameters.compressionLevel = Zip4jConstants.DEFLATE_LEVEL_NORMAL

        byteBufferList.forEach {
            zipFile.addStream(ByteArrayInputStream(it.value), zipParameters)
        }




        return zipFile.file.readBytes()
    }
}