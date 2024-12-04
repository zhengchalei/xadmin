/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.oss

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.*

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
        WIN, LINUX, UNKNOWN;

        fun defaultStoragePath(): String {
            return when (this) {
                WIN -> "c:/oss"
                LINUX -> "/oss"
                else -> "/default/path"
            }
        }
    }
}


