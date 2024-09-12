package com.zhengchalei.cloud.platform.config.jimmer

import com.zhengchalei.cloud.platform.commons.util.FileZipUtils
import java.io.ByteArrayInputStream
import java.io.InputStream
import org.babyfish.jimmer.sql.runtime.ScalarProvider
import org.springframework.stereotype.Component

@Component
class FileScalarProvider : ScalarProvider<InputStream, ByteArray> {
    override fun toScalar(sqlValue: ByteArray): InputStream {
        val decompress = FileZipUtils.decompress(sqlValue)
        return ByteArrayInputStream(decompress)
    }

    override fun toSql(scalarValue: InputStream): ByteArray {
        val compress = FileZipUtils.compress(scalarValue.readAllBytes())
        return compress
    }
}
