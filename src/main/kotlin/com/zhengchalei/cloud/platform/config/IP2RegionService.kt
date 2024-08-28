package com.zhengchalei.cloud.platform.config

import com.zhengchalei.cloud.platform.modules.sys.repository.SysDictRepository
import jakarta.annotation.PreDestroy
import org.lionsoul.ip2region.xdb.Searcher
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class IP2RegionService(
    private val sysDictRepository: SysDictRepository,
    private val iP2RegionConfigProperties: IP2RegionConfigProperties
) : ApplicationRunner {
    private val log = LoggerFactory.getLogger(IP2RegionService::class.java)

    private var searcher: Searcher? = null

    private var safe: Boolean = true

    // 部分缓存, 线程不安全
    fun notThreadSafeInit(dbPath: String) {
        try {
            val vIndex = Searcher.loadVectorIndexFromFile(dbPath)
            val searcher = Searcher.newWithVectorIndex(dbPath, vIndex)
            this.safe = false
            this.searcher = searcher
        } catch (e: Exception) {
            log.error("加载 ip2region.xdb 存储库失败 path: $dbPath", e)
            return
        }
    }

    fun defaultInit(dbPath: String) {
        // 1、从 dbPath 加载整个 xdb 到内存。
        try {
            val cBuff = Searcher.loadContentFromFile(dbPath)
            val searcher = Searcher.newWithBuffer(cBuff)
            this.searcher = searcher
        } catch (e: Exception) {
            log.error("加载 ip2region.xdb 存储库失败 path: $dbPath", e)
        }
    }

    override fun run(args: ApplicationArguments) {
        if (iP2RegionConfigProperties.enable) {
            defaultInit(iP2RegionConfigProperties.dbPath)
            this.safe = true
            return
        }
    }

    fun search(ip: String): String? {
        if (!iP2RegionConfigProperties.enable) {
            log.warn("未启动 ip2region ")
            return null
        }
        val searcher = this.searcher ?: run {
            log.warn("未配置 ip2region.xdb 存储库")
            return null
        }
        return try {
            if (this.safe) {
                synchronized(this) {
                    searcher.search(ip)
                }
            } else {
                searcher.search(ip)
            }
        } catch (e: Exception) {
            log.error("ip2region.xdb 存储库查询失败", e)
            return null
        }
    }

    @PreDestroy
    fun destroy() {
        this.searcher?.close()
    }
}
