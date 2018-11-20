package com.xxscloud.core


import java.util.*


/**
 * @author 李小双 2018.1.1
 */
object Utils {

    /**
     * 缓存一个时间
     */
    private var timeCache: Long = 0

    /**
     * 后缀.
     */
    private var timeIndex: Int = 0

    /**
     * 随机数.
     */
    private val RANDOM: Random = Random()

    /**
     * 算法
     */
    private val SNOW_FLAKE_ID_WORKER = SnowflakeIdWorker(15, 23)


    /**
     * 强制转化 .
     *
     * @param obj  Object 对象
     * @param type 转换类型
     * @param <T>  强制转换的类型Class
     * @return 不成功则为NULL
    </T> */
    fun <T> asType(obj: Any, type: Class<T>): T? {
        return if (type.isInstance(obj)) {
            obj as T
        } else {
            null
        }
    }


    /**
     * 唯一Id .
     *
     * @return 唯一Id .
     */
    fun createId(): String {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "")
    }


    /**
     * 获得一个指定范围的随机数
     *
     * @param minValue 最小值
     * @param maxValue 最大值
     * @return 随机值
     */
    fun getRandom(minValue: Int, maxValue: Int): Int {
        return RANDOM.nextInt(maxValue) % (maxValue - minValue + 1) + minValue
    }

    fun getId(): Long {
        return SNOW_FLAKE_ID_WORKER.getId()
    }

    fun getDateId(): String {
        val calendar = Calendar.getInstance()
        val month = if (calendar.get(Calendar.MONTH) > 8) calendar.get(Calendar.MONTH).toString() else "0" + (calendar.get(Calendar.MONTH) + 1)
        val day = if (calendar.get(Calendar.DAY_OF_MONTH) > 9) calendar.get(Calendar.DAY_OF_MONTH).toString() else "0" + calendar.get(Calendar.DAY_OF_MONTH)
        val id = calendar.get(Calendar.YEAR).toString() + month + day + SNOW_FLAKE_ID_WORKER.getId()
        return StringUtils.stringRight(id, id.length - 2)
    }

    fun getTempPage(): String {
        val props = System.getProperties()
        return if (props.getProperty("os.name").toString().toLowerCase().indexOf("window") > -1) "C:\\Windows\\Temp" else "/tmp"
    }

    fun getRandomText(size: Int): String {
        val tests = arrayOf("1", "2","3","4","5","6","7","8","9","0","A","B","C","D","E","F","G","H","J","K","L","M",
                "N","P","Q","R","S","T","U","V","W","X","Y","Z")
        val code = StringBuilder()
        for (i in 1..size) {
            code.append(tests[Utils.getRandom(0, tests.size)])
        }
        return code.toString()
    }
}

