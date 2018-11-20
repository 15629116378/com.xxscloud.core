package com.xxscloud.core

import java.util.*
import java.text.SimpleDateFormat
import java.util.regex.Pattern

/**
 * 字符串操作类库.
 */
object StringUtils {


    /**
     * 格式化日期.
     */
    fun formatDate(format: String): String {
        val formatter = SimpleDateFormat(format)
        return formatter.format(Date()) ?: ""
    }

    /**
     * 格式化自定义日期.
     */
    fun formatDate(data: Date, format: String): String {
        val formatter = SimpleDateFormat(format)
        return formatter.format(data) ?: ""
    }

    /**
     * 从右截取字符.
     */
    fun stringRight(data: String?, length: Int): String {
        if (data != null) {
            val i = if (data.length >= length) data.length - length else 0
            return data.substring(i)
        }
        return ""
    }


    /**
     * 从左截取字符串.
     */
    fun stringLeft(data: String?, length: Int): String {
        if (data != null) {
            return if (data.length >= length) data.substring(0, length) else data
        }
        return ""
    }

    /**
     * 格式化银行卡号.
     */
    fun formatBankCard(no: String?): String {
        if (no == null || no.trim().length < 4) {
            return no ?: ""
        }
        return return no.replace("(\\d{4})\\d{11}(\\d{4})".toRegex(), "$1 **** $2")
    }

    /**
     * 格式化身份证.
     */
    fun formatIdCard(no: String?): String {
        if (no == null || no.trim().length < 16) return no ?: ""
        return no.replace("(\\d{4})\\d{10}(\\d{4})".toRegex(), "$1 **** $2")
    }

    /**
     * 格式化手机号.
     */
    fun formatPhone(phone: String?): String {
        if (phone == null || phone.trim().length < 11) return phone ?: ""
        return phone.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1 **** $2")
    }

    /**
     * 验证邮箱.
     */
    fun emailVerification(email: String?): Boolean {
        return Pattern.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}\$", email ?: "")
    }

    /**
     * 验证手机号.
     */
    fun phoneVerification(phone: String?): Boolean {
        return Pattern.matches("^[1][3,4,5,7,8][0-9]{9}\$", phone ?: "")
    }

    /**
     * 验证银行卡.
     */
    fun bankCardVerification(bankCard: String?): Boolean {
        return Pattern.matches("^([1-9]{1})(\\d{14}|\\d{18})\$", bankCard ?: "")
    }

    /**
     * 验证身份证.
     */
    fun idCardVerification(idCard: String?): Boolean {
        val value = idCard ?: ""
        if (value.trim().isEmpty()) {
            return false
        }

        if (value.length == 18) {
            return Pattern.matches("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]\$", value)
        }
        return Pattern.matches("^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}\$", value)
    }

    /**
     * 判断是否是Long 类型
     */
    fun isLong(value: String?): Boolean {
        if (value == null || value.trim().isEmpty()) {
            return false
        }
        var i = value.length
        while (--i >= 0) {
            val c = value.toCharArray()[i]
            if (c < 48.toChar() || c > 57.toChar()) {
                return false
            }
        }
        return true
    }

    fun fillingLeft(value: Any?, fillingValue: String, length: Int): String {
        if (value == null) {
            return ""
        }
        val data = StringBuilder(value.toString())
        for (i in data.length until length) {
            data.insert(0, fillingValue)
        }
        return data.toString()
    }

    fun fillingRight(value: Any?, fillingValue: String, length: Int): String {
        if (value == null) {
            return ""
        }
        val data = StringBuilder(value.toString())
        for (i in data.length until length) {
            data.append(fillingValue)
        }
        return data.toString()
    }


}