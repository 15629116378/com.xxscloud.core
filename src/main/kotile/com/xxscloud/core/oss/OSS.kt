package com.xxscloud.core.oss

import com.xxscloud.core.Algorithm
import com.xxscloud.core.HttpApiUtils
import com.xxscloud.core.JsonUtils
import com.xxscloud.core.SystemException
import com.xxscloud.core.data.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 对象存储引擎使用类封装
 */
@ConfigurationProperties(prefix = "spring.oss")
open class OSS : HttpApiUtils() {

    private val logger: Logger = LoggerFactory.getLogger(OSS::class.java)

    lateinit var url: String
    lateinit var groupId: String

    /**
     * 上传文件.
     */
    fun put(filePath: String, fileData: ByteArray, fileName: String?, type: String?): OSSEntity? {
        logger.info("${this.url} || ${this.groupId}")
        logger.info("上传 格式为 $type, $fileName 到 $filePath")

        if (this.url.isEmpty()) {
            throw SystemException("url is null ?")
        }
        if (this.groupId.isEmpty()) {
            throw SystemException("groupId is null ?")
        }

        val md5: String = Algorithm.md5Encryption(fileData)!!
        val path: String = if (filePath.isEmpty()) "/" else filePath
        val size = fileData.size

        val httpResponseEntity: HttpResponseEntity = get("${this.url}/getData/$md5")
        if (httpResponseEntity.succeed) {
            val resultDto: ResultDto = JsonUtils.parseObject(httpResponseEntity.bodyString!!, ResultDto::class.java)
            if (resultDto.code == ResultEnum.SUCCEED) {
                val ossEntity = JsonUtils.parseObject(JsonUtils.stringify(resultDto.data), OSSEntity::class.java)
                if (ossEntity.id != null) {
                    logger.info("找到对应MD5 $fileName")
                    return ossEntity
                }
            }

            logger.info("开始上传文件 $fileName ...")
            val newOSS = OSSEntity(groupId = this.groupId, path = path, name = fileName, size = size.toLong(), md5 = md5, type = type)
            val headers = HashMap<String, String>()
            headers["oss-info"] = JsonUtils.stringify(newOSS)
            headers["oss-authentication"] = ""
            val response: HttpResponseEntity = put("${this.url}/put", headers, fileData)
            if (response.succeed) {
                logger.info("上传成功")
                val result = JsonUtils.parseObject(response.bodyString!!, ResultDto::class.java)
                if (result.code == ResultEnum.SUCCEED) {
                    val ossEntity = JsonUtils.parseObject(JsonUtils.stringify(result.data), OSSEntity::class.java)
                    if (ossEntity.id != null) {
                        return ossEntity
                    }
                }
            }
            logger.error("上传失败，${response.bodyString}")
        }
        logger.error("获取数据失败，${httpResponseEntity.bodyString}")
        return null
    }

}