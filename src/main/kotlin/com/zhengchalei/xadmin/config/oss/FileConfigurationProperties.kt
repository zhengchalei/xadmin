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
package com.zhengchalei.xadmin.config.oss

import java.util.*
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "file")
class FileConfigurationProperties {

    var storage: FileStorageType = FileStorageType.LOCAL
    var maxSize: Long = 1024 * 1024 * 1024
    var local: LocalStorageConfiguration = LocalStorageConfiguration()

    enum class FileStorageType {
        LOCAL,
        DATABASE,
        MINIO,
    }
}

class LocalStorageConfiguration {

    var platform: Platform = detectPlatform()
    var storagePath: String = platform.defaultStoragePath()

    private fun detectPlatform(): Platform {
        val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
        return when {
            osName.contains("win") -> Platform.WIN
            osName.contains("nix") || osName.contains("nux") || osName.contains("mac") -> Platform.LINUX
            else -> Platform.UNKNOWN
        }
    }

    enum class Platform {
        WIN,
        LINUX,
        UNKNOWN;

        fun defaultStoragePath(): String {
            return when (this) {
                WIN -> "c:/oss"
                LINUX -> "/oss"
                else -> "/default/path"
            }
        }
    }
}
