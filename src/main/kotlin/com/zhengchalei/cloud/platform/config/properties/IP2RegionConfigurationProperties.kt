package com.zhengchalei.cloud.platform.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "ip2region")
class IP2RegionConfigurationProperties {
    var enable: Boolean = false

    var dbPath: String = ""
}
