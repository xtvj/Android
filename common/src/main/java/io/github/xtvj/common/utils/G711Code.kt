package io.github.xtvj.common.utils

object G711Code {
    private const val SIGN_BIT = 0x80
    private const val QUANT_MASK = 0xf
    private const val SEG_SHIFT = 4
    private const val SEG_MASK = 0x70
    private var seg_end = shortArrayOf(0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF)
    private fun search(`val`: Short, table: ShortArray, size: Short): Short {
        for (i in 0 until size) {
            if (`val` <= table[i]) {
                return i.toShort()
            }
        }
        return size
    }

    private fun linearToAlaw(pcm: Short): Byte {
        var pcmTemp = pcm
        val mask: Short
        var aTemp: Char
        if (pcmTemp >= 0) {
            mask = 0xD5 //* sign (7th) bit = 1 二进制的11010101
        } else {
            mask = 0x55 //* sign bit = 0  二进制的01010101
            pcmTemp = (-pcmTemp - 1).toShort() //负数转换为正数计算
            if (pcmTemp < 0) {
                pcmTemp = 32767
            }
        }

        /* Convert the scaled magnitude to segment number. */
        val seg: Short = search(pcmTemp, seg_end, 8.toShort()) //查找采样值对应哪一段折线

        /* Combine the sign, segment, and quantization bits. */return if (seg >= 8) /* out of range, return maximum value. */ (0x7F xor mask.toInt()).toByte() else {
            //以下按照表格第一二列进行处理，低4位是数据，5~7位是指数，最高位是符号
            aTemp = (seg.toInt() shl SEG_SHIFT).toChar()
            aTemp =
                if (seg < 2) (aTemp.code or (pcmTemp.toInt() shr 4 and QUANT_MASK)).toChar() else (aTemp.code or (pcmTemp.toInt() shr seg + 3 and QUANT_MASK)).toChar()
            (aTemp.code xor mask.toInt()).toByte()
        }
    }

    fun alaw2linear(aByte: Byte): Short {
        var tempByte = aByte
        var t: Short
        tempByte = (tempByte.toInt() xor 0x55).toByte()
        t = (tempByte.toInt() and QUANT_MASK shl 4).toShort()
        when (val seg: Short = (tempByte.toInt() and SEG_MASK shr SEG_SHIFT).toShort()) {
            (0).toShort() -> {
                t = (t + 8).toShort()
            }
            (1).toShort() -> {
                t = (t + 0x108).toShort()
            }
            else -> {
                t = (t + 0x108).toShort()
                t = (t.toInt() shl seg - 1).toShort()
            }
        }
        return if (tempByte.toInt() and SIGN_BIT != 0) t else (-t).toShort()
    }

    /**
     * pcm 转 G711 a率
     */
    fun G711aEncoder(pcm: ShortArray, code: ByteArray, size: Int) {
        for (i in 0 until size) {
            code[i] = linearToAlaw(pcm[i])
        }
    }

    /**
     * G.711 转 PCM
     */
    fun G711aDecoder(pcm: ShortArray, code: ByteArray, size: Int) {
        for (i in 0 until size) {
            pcm[i] = alaw2linear(code[i])
        }
    }
}