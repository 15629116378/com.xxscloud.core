package com.xxscloud.core

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage


/**
 * @author 李小双 2018.4.27
 * 图像处理类库.
 */
object ImageUtils {

    /**
     * 生成验证码.
     */
    fun generateVerification(code: String): BufferedImage {
        val imageWidth = 240
        val imageHeight = 96
        val interferenceLineCount = 3
        val colors = arrayOf(Color(40, 150, 70),
                Color(232, 180, 123),
                Color(241, 124, 103),
                Color(31, 111, 181))

        val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)
        val graphics = image.graphics


        //绘画背景
        graphics.fillRect(0, 0, imageWidth, imageHeight)


        //画干扰线
        for (i in 0..interferenceLineCount) {
            graphics.color = colors[Utils.getRandom(0, colors.size - 1)]
            graphics.drawLine(Utils.getRandom(0, imageWidth), Utils.getRandom(0, imageHeight),
                    Utils.getRandom(0, imageWidth), Utils.getRandom(0, imageHeight))
        }


        //写入字体
        val fontSize = (imageHeight * 0.7).toInt()
        var offset = 0
        graphics.font = Font("微软雅黑", Font.PLAIN, fontSize)
        graphics.color = colors[Utils.getRandom(0, colors.size - 1)]
        for (i in 0 until code.length) {
            val x = Utils.getRandom(-8, 8)
            val y = Utils.getRandom(-8, 8)
            graphics.drawString(code[i].toString(), offset, fontSize)
            offset += ((imageWidth / code.length) * (Math.random() * 0.3 + 0.7)).toInt()
            graphics.translate(-x, -y)
        }


        //绘画完成
        graphics.dispose()
        return image
    }

    fun rgbToInt(color: Color): Int {
        return 0xFF shl 24 or (color.red shl 16) or (color.green shl 8) or color.blue
    }

    fun intToRgb(color: Int): Color {
        val r = 0xFF and color
        var g = 0xFF00 and color
        g = g shr 8
        var b = 0xFF0000 and color
        b = b shr 16
        return Color(r, g, b)
    }
}

