package com.zhengchalei.cloud.platform.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "file")
@EnableConfigurationProperties
class FileConfigurationProperties {
    var storage: FileStorageType = FileStorageType.LOCAL

    val local: LocalStorageConfigurationProperties = LocalStorageConfigurationProperties()

    val database: DatabaseStorageConfigurationProperties = DatabaseStorageConfigurationProperties()

    fun getConfig(): StorageConfigurationProperties =
        when (storage) {
            FileStorageType.LOCAL -> local
            FileStorageType.DATABASE -> database
        }

    enum class FileStorageType {
        LOCAL,
        DATABASE,
    }

    open class StorageConfigurationProperties {
        var storagePath: String = ""

        // 最大大小
        var maxSize: Long = 1024 * 1024 * 1024
    }

    class LocalStorageConfigurationProperties : StorageConfigurationProperties() {
        var platform: Platform = Platform.LINUX

        enum class Platform {
            WIN,
            LINUX,
            MACOS,
        }
    }

    class DatabaseStorageConfigurationProperties : StorageConfigurationProperties()
}
