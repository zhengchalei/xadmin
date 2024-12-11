/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.zhengchalei.xadmin.commons.util

import java.io.ByteArrayOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater

object FileZipUtils {
    const val BITE_SIZE: Int = 4 * 1024

    fun compress(data: ByteArray): ByteArray {
        val deflater = Deflater()
        deflater.setLevel(Deflater.BEST_COMPRESSION)
        deflater.setInput(data)
        deflater.finish()
        val outputStream = ByteArrayOutputStream(data.size)
        val tmp = ByteArray(BITE_SIZE)
        while (!deflater.finished()) {
            val size = deflater.deflate(tmp)
            outputStream.write(tmp, 0, size)
        }
        outputStream.close()
        return outputStream.toByteArray()
    }

    fun decompress(data: ByteArray): ByteArray {
        val inflater = Inflater()
        inflater.setInput(data)
        val outputStream = ByteArrayOutputStream(data.size)
        val tmp = ByteArray(BITE_SIZE)
        while (!inflater.finished()) {
            val count = inflater.inflate(tmp)
            outputStream.write(tmp, 0, count)
        }
        outputStream.close()
        return outputStream.toByteArray()
    }
}
