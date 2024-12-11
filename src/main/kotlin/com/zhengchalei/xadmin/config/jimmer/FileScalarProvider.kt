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
package com.zhengchalei.xadmin.config.jimmer

import com.zhengchalei.xadmin.commons.util.FileZipUtils
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
