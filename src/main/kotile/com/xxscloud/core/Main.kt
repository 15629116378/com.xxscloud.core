import com.xxscloud.core.*
import com.xxscloud.core.oss.OSS
import java.awt.Color
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import javax.imageio.ImageIO

//import com.xxscloud.core.SnowflakeIdWorker
//import com.xxscloud.core.StringUtils
//import com.xxscloud.core.Utils
//import com.xxscloud.core.api.ApiCoreController
//import org.apache.curator.retry.ExponentialBackoffRetry
//import org.apache.curator.framework.CuratorFrameworkFactory
//import org.apache.curator.framework.CuratorFramework
//import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex
//import org.apache.curator.framework.recipes.locks.InterProcessLock
//import java.util.concurrent.TimeUnit
//
//
////package com.xxscloud.core
////
////import com.xxscloud.core.data.HttpResponseEntity
////
//
//class t : ApiCoreController() {
//
//}
//
fun main(args: Array<String>) {
    //val file = File(Paths.get("D:\\1.png").toUri())
//    val file2 =File(Paths.get("D:\\1.xml").toUri())
//
//
//
//    val d = HashMap<String,ByteArray>()
//    d["1.png"] = file.readBytes()
//    d["1.xml"] = file2.readBytes()
//
//    val fos = FileOutputStream(File("d:\\1.zip"))
//    val bos = BufferedOutputStream(fos);
//    bos.write(ZipUtils.toZip(d,"123"))

//    val s = OSS()
//    s.put("/hg", file.readBytes(), "g1234", "png")


//    val d = ImageUtils.generateVerification(Utils.getRandomText(4))
//     ImageIO.write(d, "jpg", Paths.get("d:\\1.jpg").toFile())
//
//
//    FileUtils.writeAll("D:\\d\\eeee\\2.jpg",Paths.get("d:\\1.jpg").toFile().readBytes())


    //val png = FileUtils.readAll()
    val img = ImageIO.read(File("C:\\Users\\Administrator\\Pictures\\2.PNG"))
    val color = arrayOf(243, 25, 15)

    for (x in 0 until img.width) {
        for (y in 0 until img.height) {
            val pixel = img.getRGB(x, y)
            val rgb = arrayOf(pixel and 0xff0000 shr 16, pixel and 0xff00 shr 8, pixel and 0xff)
            println(JsonUtils.stringify(rgb))
            val s = 120
            if (rgb[0] > color[0] + s || rgb[0] < color[0] - s ||
                    rgb[1] > color[1] + s || rgb[1] < color[1] - s ||
                    rgb[2] > color[2] + s || rgb[2] < color[2] - s) {
                img.setRGB(x, y, ImageUtils.rgbToInt(Color.WHITE))
            } else {
                img.setRGB(x, y, ImageUtils.rgbToInt(Color.BLACK))
            }
        }
    }

    val outputfile = File("C:\\Users\\Administrator\\Pictures\\saved.png")
    ImageIO.write(img, "png", outputfile)

}