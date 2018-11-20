package com.xxscloud.core


import com.google.gson.JsonObject

import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory



class RedisUtils(private val redisTemplate: RedisTemplate<Any, Any>) {

    private val stringSerializer = StringRedisSerializer()
    private val jdkSerializationRedisSerializer = JdkSerializationRedisSerializer()
    private val log: Logger = LoggerFactory.getLogger(RedisUtils::class.java)


    private fun redisSerializer() {
        redisTemplate.keySerializer = stringSerializer
        redisTemplate.valueSerializer = stringSerializer
        redisTemplate.hashKeySerializer = jdkSerializationRedisSerializer
        redisTemplate.hashValueSerializer = jdkSerializationRedisSerializer
    }

    /* 读取值 */
    private fun get(key: String): Any? {
        redisSerializer()
        return redisTemplate.opsForValue().get(key)
    }

    fun getString(key: String): String? {
        val obj = get(key)
        return obj?.toString()
    }

    fun getInt(key: String): Int? {
        val obj = get(key)
        return if (obj != null) Integer.valueOf(obj.toString()) else null
    }

    fun getInt(key: String, default: Int): Int {
        val obj = get(key)
        return if (obj != null) Integer.valueOf(obj.toString()) else default
    }

    fun getLong(key: String): Long? {
        val obj = get(key)
        return obj?.toString()?.toLong()
    }

    fun getLong(key: String, default: Long): Long {
        val obj = get(key)
        return obj?.toString()?.toLong() ?: default
    }

    fun getBoolean(key: String): Boolean? {
        val obj = get(key)
        return obj?.toString()?.toBoolean()
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        val obj = get(key)
        return obj?.toString()?.toBoolean() ?: default
    }

    fun getJsonObject(key: String): JsonObject? {
        val obj = get(key)
        return if (obj != null) JsonUtils.parseObject(obj.toString()) else null
    }

    fun <T> getJsonObject(key: String, clazz: Class<*>): T? {
        val obj = get(key)
        return if (obj != null) JsonUtils.parseObject(obj.toString(), clazz) as T else null
    }


    /* 其他操作 */
    fun exists(key: String): Boolean {
        return redisTemplate.execute {
            it.exists(key.toByteArray())
        } ?: false
    }

    fun expire(key: String, time: Int): Boolean {
        return redisTemplate.expire(key, time.toLong(), TimeUnit.SECONDS)
    }


    /*删除和新增和修改*/
    fun del(key: String): Boolean {
        redisSerializer()
        return redisTemplate.delete(key) ?: false
    }

    fun incr(key: String, default: Long): Long {
        redisTemplate.valueSerializer = GenericToStringSerializer(Long::class.java)
        return redisTemplate.opsForValue().increment(key, 1) ?: default
    }

    fun incr(key: String): Long? {
        return incr(key, 1)
    }

    fun setex(key: String, time: Int, value: String): Boolean {
        redisSerializer()
        redisTemplate.opsForValue().set(key, value, time.toLong(), TimeUnit.SECONDS)
        return true
    }


    fun setObject(key: String, time: Int, obj: Any): Boolean {
        redisSerializer()
        redisTemplate.opsForValue().set(key, JsonUtils.stringify(obj), time.toLong(), TimeUnit.SECONDS)
        return true
    }


    /*锁*/

    fun tryLock(lock: String, timeout: Long, automatic: Boolean, automaticTimeout: Long, transactionTimeout: Long): Boolean {
        /**
         * 分布式锁.
         *  key 锁的唯一值
         *  timeout 锁等待超时时间
         *  auto 锁是否自动释放
         *
         *  1. setnx 设置值是，标示获取锁成功
         *  2. 如果有设置等待时间则等待后在重试
         *  3. 如果有超时时间则设置键值对过期时间
         *  4. 如果超时且异常的Key 做处理
         */

        redisSerializer()

        val lockId = "LOCK:$lock"

        var lockStatus = false
        val sTime: Long = System.currentTimeMillis()
        try {
            redisTemplate.execute {
                do {
                    //创建初始变量
                    val time: Long = System.currentTimeMillis() + transactionTimeout
                    val value = "${Utils.createId()}$time"

                    val result: Boolean =
                            it.setNX(lockId.toByteArray(), value.toByteArray()) ?: false

                    //如果成功
                    if (result) {
                        getLock(lockId, automatic, automaticTimeout)
                        lockStatus = true
                        break
                    }

                    //如果失败
                    val oldValue: String = getString(lockId) ?: ""
                    val oldTime: Long = (if (oldValue.length >= 32) oldValue.substring(32) else "0").toLong()
                    if (oldTime < System.currentTimeMillis()) {
                        val oValue: ByteArray? = it.getSet(lockId.toByteArray(), value.toByteArray())
                        lockStatus = if (oValue == null) false else String(oValue) == oldValue
                        if (lockStatus) {
                            getLock(lockId, automatic, automaticTimeout)
                        }
                        break
                    }

                    //是否需要等待
                    if (timeout <= 0) {
                        break
                    }
                    Thread.sleep(50)
                } while ((System.currentTimeMillis() - sTime) <= timeout)
            }
        } catch (ex: Exception) {
            log.error("redis lock error", ex)
        } finally {

        }
        return lockStatus
    }

    fun tryLock(lock: String, timeout: Long, transactionTimeout: Long): Boolean {
        return tryLock(lock, timeout, false, 0, transactionTimeout)
    }

    fun tryLock(lock: String, transactionTimeout: Long): Boolean {
        return tryLock(lock, 1000 * 60 * 10, false, 0, transactionTimeout)
    }

    fun unlock(lock: String): Boolean {
        val lockId = "LOCK:$lock"
        return del(lockId)
    }

    /**
     * 获得锁.
     */
    fun getLock(lockId: String, automatic: Boolean, automaticTimeout: Long) {
        val maxTimeout: Int = 60 * 5
        if (automatic) {
            expire(lockId, (automaticTimeout / 1000).toInt())
        } else {
            expire(lockId, maxTimeout)
        }
    }
}