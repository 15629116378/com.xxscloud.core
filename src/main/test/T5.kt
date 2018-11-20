//import com.sun.org.apache.xpath.internal.operations.Bool
//import com.xxscloud.core.Utils
//import org.junit.Test
//
//class T5 {
//
//
//    /**
//     * 第四题
//     */
//    @Test
//    fun t4() {
//
//        var arrays = ArrayList<ArrayList<String>>()
//        for (x in 0..10) {
//            val a = ArrayList<String>()
//            for (y in 0..10) {
//                a.add(Utils.getRandom(0, 10).toString())
//            }
//            arrays.add(a)
//        }
//
//        var a1 = arrayListOf("2", "3", "4", "8", "90")
//        var a2 = arrayListOf("5", "7", "9", "12", "9")
//        var a3 = arrayListOf("1", "0", "6", "10", "3")
//        var a4 = arrayListOf("1", "0", "6", "10", "3")
//        var a5 = arrayListOf("1", "0", "6", "10", "3")
//        var a6 = arrayListOf("1", "0", "6", "10", "3")
//        var a7 = arrayListOf("1", "0", "6", "10", "3")
//        arrays = arrayListOf(a1, a2, a3, a4, a5, a6, a7)
//
//        println("随机数组为$arrays")
//        arrays.forEach {
//            println("$it")
//        }
//        rotate(arrays)
//
//    }
//
//    fun rotate(arrays: ArrayList<ArrayList<String>>): String {
//
//        val mX = arrays[0].size - 1
//        val mY = arrays.size - 1
//
//        var index = 0
//        while (true) {
//
//
//            //一圈
//            for (x in index..mX - index) {
//                print(arrays[index][x] + " ")
//            }
//
//            if (mY - index <= (index + 1)) {
//                break
//            }
//
//            for (y in (index + 1)..mY - index) {
//                print(arrays[y][mX - index] + " ")
//            }
//
//
//
//            for (x in (index..mX - (index + 1)).reversed()) {
//                print(arrays[mY - index][x] + " ")
//            }
//
//            for (y in ((index + 1)..mY - (index + 1)).reversed()) {
//                print(arrays[y][index] + " ")
//            }
//
//            println()
//            index++
//        }
//
//        return ""
//    }
//
//    /**
//     * 第三题
//     */
//    @Test
//    fun t3() {
//        var arrays = ArrayList<ArrayList<Boolean>>()
//        for (x in 0..9) {
//            val a = ArrayList<Boolean>()
//            for (y in 0..9) {
//                val s = Utils.getRandom(0, 10)
//                a.add(s % 2 == 0 || s == 3 || s == 9)
//            }
//            arrays.add(a)
//        }
//
//
//        var a1 = arrayListOf(true, true, true, false)
//        var a2 = arrayListOf(true, true, true, false)
//        var a3 = arrayListOf(true, true, true, false)
//        var a4 = arrayListOf(true, true, true, true)
//        var a5 = arrayListOf(false, true, true, false)
//        var a6 = arrayListOf(true, false, true, false)
//        arrays = arrayListOf(a1, a2, a3, a4, a5, a6)
//
//        val moduleList = getModule(arrays)
//        moduleList.forEach {
//            println(it)
//        }
//        arrays.forEach {
//            it.forEach {
//                print(if (it) "#" else "-")
//                print(" ")
//            }
//            println()
//        }
//
//    }
//
//
//    fun getModule(array: ArrayList<ArrayList<Boolean>>): ArrayList<Module> {
//        val r = ArrayList<Module>()
//        //穷举算法所有可能
//        for (i in 0 until array.size) {
//            for (j in 0 until array[i].size) {
//                r.addAll(g(i, j, i, j, array))
//            }
//        }
//
//
//        //过滤掉数据
//        val mX = array[0].size - 1
//        val mY = array.size - 1
//        r.removeIf {
//            val x = it.x2 - it.x1 + 1
//            val y = it.y2 - it.y1 + 1
//            it.m = (x * y).toDouble()
//            var status = isModule(it, array)
//            //过滤是否符合第一条件
//            if (status) {
//                status = (mX - it.y2 + it.y1) % 2 == 0 && (mY - it.x2 + it.x1) % 2 == 0
//                status = if (status && it.m >= 8) moveModule(it, it.y2 - it.y1 - 1, (mY - it.x2 + it.x1) / 2, array) else status
//            }
//            !status
//        }
//
//        r.sortByDescending { it.m }
//        return r
//    }
//
//    fun g(x1: Int, y1: Int, x2: Int, y2: Int, array: ArrayList<ArrayList<Boolean>>): ArrayList<Module> {
//        val r = ArrayList<Module>()
//
//        //添加到模型
//        r.add(Module("$x1,$y1:$x2,$y2", x1, y1, x2, y2))
////        r.add(Module("$x1,$y1:$x2,${y2 + 1}"))
////        r.add(Module("$x1,$y1:${x2 + 1},$y2"))
////        r.add(Module("$x1,$y1:${x2 + 1},${y2+1}"))
//
//        if (y2 + 1 < array[0].size) {
//            r.addAll(gX(x1, y1, x2, y2 + 1, array))
//        }
////
////        //val yy = y2 - y1
//        if (x2 + 1 < array.size) {
//            r.addAll(gY(x1, y1, x2 + 1, y2, array))
//        }
//
//
//        if (y2 + 1 < array[0].size && x2 + 1 < array.size) {
//            r.addAll(g(x1, y1, x2 + 1, y2 + 1, array))
//        }
//
////        r.add(Module("$x1,$x2:$y1,${y2 + 1}"))
////
////
////        r.add(Module("$x1,${x2 + 1}:$y1,${y2 + 1}"))
//
//
////        if (x2 + 1 < array[0].size) {
////            r.addAll(g(x1, y1, x2 + 1, y2, array))
////        }
////        if (y2 + 1 < array.size) {
////            r.addAll(g(x1, y1, x2, y2 + 1, array))
////        }
//        return r
//    }
//
//    fun gX(x1: Int, y1: Int, x2: Int, y2: Int, array: ArrayList<ArrayList<Boolean>>): ArrayList<Module> {
//        val r = ArrayList<Module>()
//
//        //添加到模型
//        r.add(Module("$x1,$y1:$x2,$y2", x1, y1, x2, y2))
//
//        if (y2 + 1 < array[0].size) {
//            r.addAll(gX(x1, y1, x2, y2 + 1, array))
//        }
//
//        return r
//    }
//
//    fun gY(x1: Int, y1: Int, x2: Int, y2: Int, array: ArrayList<ArrayList<Boolean>>): ArrayList<Module> {
//        val r = ArrayList<Module>()
//
//        //添加到模型
//        r.add(Module("$x1,$y1:$x2,$y2", x1, y1, x2, y2))
//
//        if (x2 + 1 < array.size) {
//            r.addAll(gY(x1, y1, x2 + 1, y2, array))
//        }
//
//        return r
//    }
//
//    fun moveModule(module: Module, x: Int, y: Int, array: ArrayList<ArrayList<Boolean>>): Boolean {
//
//        if (!isModule(module, array)) {
//            return false
//        }
//
//        if (isCenter(module, array)) {
//            return true
//        }
//
//        var status = false
//        if (x - 1 >= 0) {
//            module.y1 = module.y1 + 1
//            module.y2 = module.y2 + 1
//            status = moveModule(module, x - 1, y, array)
//            if (status) {
//                return status
//            }
//        }
//
//        if (y - 1 >= 0) {
//            module.x1 = module.x1 + 1
//            module.x2 = module.x2 + 1
//            status = moveModule(module, x - 1, y, array)
//            if (status) {
//                return status
//            }
//        }
//        return false
//    }
//
//    fun isCenter(module: Module, array: ArrayList<ArrayList<Boolean>>): Boolean {
//        val mX = array[0].size - 1
//        val mY = array.size - 1
//        var status = false
//        if ((mX - (module.y2 - module.y1)) % 2 == 0) {
//            status = (mX - (module.y2 - module.y1)) / 2 == module.y1
//        }
//
//        if (status && (mY - (module.x2 - module.x1)) % 2 == 0) {
//            status = (mY - (module.x2 - module.x1)) / 2 == module.x1
//        }
//
//        return status
//    }
//
//    fun isModule(module: Module, array: ArrayList<ArrayList<Boolean>>): Boolean {
//        var status = true
//        for (i in module.x1..module.x2) {
//            for (k in module.y1..module.y2) {
//                if (!array[i][k]) {
//                    status = false
//                    break
//                }
//            }
//        }
//        return status
//    }
//
//
//    /**
//     * 第一题
//     */
//    @Test
//    fun t1() {
//        var arrays = ArrayList<Int>()
//        for (i in 0..9) {
//            arrays.add(Utils.getRandom(0, 100))
//        }
//
//        arrays = arrayListOf(5, 2, 4, 6, 3, 7)
//
//        arrays.forEach {
//            print("$it ")
//        }
//        println()
//        println("最低成本: ${getGroup(arrays)}")
//
//    }
//
//    fun getGroup(array: ArrayList<Int>): Group {
//        val data = ArrayList<Group>()
//        for (i in 1 until array.size - 3) {
//            for (k in i + 2 until array.size - 1) {
//                //添加组合
//                data.add(Group(id = "$i,$k", value = array[i] + array[k]))
//            }
//        }
//        data.forEach {
//            println(it)
//        }
//        data.sortBy { it.value }
//        return data[0]
//    }
//
//
//    /**
//     * 第二题
//     */
//    @Test
//    fun t2() {
//        val arrays = ArrayList<Int>()
//        val m = 5
//        //实现方法1
//        for (i in 0..100) {
//            arrays.add(Utils.getRandom(0, 1000000))
//        }
//        arrays.sortByDescending { it }
//
//        for (i in 0 until  m) {
//            println(arrays[i])
//        }
//
//        //冒泡算法
//
//    }
//
//
//    /**
//     * 第五大题
//     *
//     * 1. 使用二分算法，根据楼层高度进行二分，在第60层进行测试如果摔坏则到第30层进行测试，
//
//        2. 还有一种从第一层开始测试, 一直测试到摔坏层，比如 30层摔不坏 31层摔坏则第三十层就是最高层，可以不用多摔坏一个IPHONE ， 这个IPHONE可以给我用了 哈哈哈
//
//        二分法最坏会是7次， 第二种方法只有最坏有120次
//     *
//     */
//
//
//
//}
//
//
//data class Module(
//        val id: String,
//        var x1: Int,
//        var y1: Int,
//        var x2: Int,
//        var y2: Int,
//        var m: Double = 0.0,
//        var s: Boolean = true
//)
//
//
//data class Group(
//        val id: String,
//        var value: Int
//)
//
//
//
//
//
//
//
//
