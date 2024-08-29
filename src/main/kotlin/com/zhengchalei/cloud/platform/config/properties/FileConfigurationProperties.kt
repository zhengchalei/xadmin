package com.zhengchalei.cloud.platform.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "file")
@EnableConfigurationProperties
class FileConfigurationProperties {
    var storageType: FileStorageType = FileStorageType.LOCAL

    val localConfig: LocalConfigurationProperties = LocalConfigurationProperties()
}

enum class FileStorageType {
    LOCAL,
}

class LocalConfigurationProperties {
    var path: String = ""
}
