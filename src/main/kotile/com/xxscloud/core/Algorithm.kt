package com.xxscloud.core

import org.apache.commons.codec.binary.Base64
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.nio.MappedByteBuffer
import java.nio.charset.Charset
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.spec.DESKeySpec
import java.security.*
import javax.crypto.SecretKey
import sun.misc.BASE64Decoder
import javax.crypto.SecretKeyFactory


/**
 * @author 李小双 2018.1.1
 * 算法封装类库
 */
object Algorithm {

    /**
     * MD5 加密算法.
     *
     * @param text 加密内容.
     * @return 签名结果.
     */
    fun md5Encryption(text: String): String? {
        val messageDigest: MessageDigest
        try {
            messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(text.toByteArray(charset("utf-8")))
            val resultByte = messageDigest.digest()
            val value = 256
            val size = 16

            val resultBuffer = StringBuilder()
            for (temp in resultByte) {
                var v: Int = Integer.valueOf(temp.toString())
                if (temp < 0) {
                    v += value
                } else if (temp < size) {
                    resultBuffer.append("0")
                }
                resultBuffer.append(Integer.toHexString(v))
            }
            val result = resultBuffer.toString()
            return if (result == "") {
                null
            } else {
                result.toUpperCase()
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 计算文件的MD5.
     *
     * @param file 文件的字节数
     * @return MD5 值
     */
    fun md5Encryption(file: MappedByteBuffer): String? {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(file)
            val startIndex = 1
            val value = 16
            val bigInteger = BigInteger(startIndex, md5.digest())
            val result = bigInteger.toString(value)
            return if (result == "") null else result.toUpperCase()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 计算文件的MD5
     */
    fun md5Encryption(file: ByteArray): String? {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(file)
            val startIndex = 1
            val value = 16
            val bigInteger = BigInteger(startIndex, md5.digest())
            val result = bigInteger.toString(value)
            return if (result == "") null else result.toUpperCase()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * RSA 加密算法.
     *
     * @param text 加密内容
     * @param key  秘钥
     * @return 签名结果
     */
    fun rsaEncryption(text: String, key: String): String? {
        return "RSA"
    }

    /**
     * RSA 加密算法.
     */
    fun rsaEncryption(text: String, key: String, iv: String): String? {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(key.toByteArray())
        val publicKey: RSAPublicKey = keyFactory.generatePublic(keySpec) as RSAPublicKey

        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return "RSA"
    }

    /**
     * RSA 加密算法.
     *
     * @param signature 签名内容
     * @param key       秘钥
     * @return 数据源
     */
    fun rsaDecryption(signature: String, key: String): String? {
        return "RSA"
    }

    /**
     * AES 加密算法.
     *
     * @param text 加密内容
     * @param key  秘钥
     * @return 签名结果
     */
    fun aesEncryption(text: String, key: String): String? {
        //秘钥长度
        val keyLength = 16
        if (keyLength == key.length) {
            try {
                val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key.toByteArray(charset("utf-8")), "AES"))
                val resultByte = cipher.doFinal(text.toByteArray(charset("utf-8")))
                val result = Base64.encodeBase64String(resultByte)
                return if (result == null || result == "") null else result.toUpperCase()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }

        }
        return null
    }

    /**
     * AES 解密算法.
     *
     * @param signature 签名内容
     * @param key       秘钥
     * @return 数据源
     */
    fun aesDecryption(signature: String, key: String): String? {
        //秘钥长度
        val keyLength = 16
        if (keyLength == key.length) {
            try {
                val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
                cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key.toByteArray(charset("utf-8")), "AES"))
                val resultByte = cipher.doFinal(Base64.decodeBase64(signature))
                val result = java.lang.String(resultByte, "UTF-8") as String
                return if (result == "") null else result.toUpperCase()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * HmacSHA1 加密.
     *
     * @param text 加密内容
     * @param key  Key
     * @return 处理结果
     */
    fun hmacSha1Encryption(text: String, key: String): String? {
        try {
            val secretKey = SecretKeySpec(key.toByteArray(charset("utf-8")), "HmacSHA1")
            val mac = Mac.getInstance("HmacSHA1")
            mac.init(secretKey)
            val textBytes = text.toByteArray(charset("utf-8"))
            val result = Base64.encodeBase64String(mac.doFinal(textBytes))
            return if (result == null || result == "") null else result.replace("\r\n", "").toUpperCase()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * AES CBC加密.
     */
    fun aesCBCEncryption(text: String, key: String, iv: String): String? {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val raw = key.toByteArray()
        val keySpec = SecretKeySpec(raw, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(iv.toByteArray()))
        val encrypted = cipher.doFinal(text.toByteArray())
        return Base64.encodeBase64String(encrypted).trim().replace("\r", "").replace("\n", "")
    }

    /**
     * 二进制转十六进制.
     */
    fun hexString(bytes: ByteArray): String {
        var r = ""
        for (byte in bytes) {
            var hex = Integer.toHexString(byte.toInt() and 0xFF)
            if (hex.length == 1) {
                hex = "0$hex";
            }
            r += hex.toUpperCase();
        }
        return r
    }

    /**
     * AES CBC解密.
     */
    fun aesCBCDecryption(text: String, key: String, iv: String): String? {

        val raw = key.toByteArray(Charset.forName("ASCII"))
        val keySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivParameterSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec)
        val value = Base64.decodeBase64(text)
        val original = cipher.doFinal(value)
        return String(original)
    }

    /**
     * Des 加密.
     */
    fun desEncryption(text: String, key: String): String {
        try {
            val datasource = text.toByteArray(Charset.forName("UTF-8"))
            val random = SecureRandom()
            val desKey = DESKeySpec(key.toByteArray(Charset.forName("UTF-8")))
            val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance("DES")
            val securekey: SecretKey = keyFactory.generateSecret(desKey)
            //Cipher对象实际完成加密操作
            val cipher = Cipher.getInstance("DES")
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random)
            //现在，获取数据并加密
            //正式执行加密操作
            return sun.misc.BASE64Encoder().encode(cipher.doFinal(datasource))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * Des 解密.
     */
    fun desDecryption(text: String, key: String): String {
        // DES算法要求有一个可信任的随机数源
        val random = SecureRandom()
        // 创建一个DESKeySpec对象
        val desKey = DESKeySpec(key.toByteArray(Charset.forName("UTF-8")))
        // 创建一个密匙工厂
        val keyFactory = SecretKeyFactory.getInstance("DES")
        // 将DESKeySpec对象转换成SecretKey对象
        val securekey = keyFactory.generateSecret(desKey)
        // Cipher对象实际完成解密操作
        val cipher = Cipher.getInstance("DES")
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random)
        // 真正开始解密操作
        val decoder = BASE64Decoder()
        return String(cipher.doFinal(decoder.decodeBuffer(text)), Charset.forName("UTF-8"))
    }

}
