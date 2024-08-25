package com.zhengchalei.cloud.platform.config

import com.zhengchalei.cloud.platform.modules.sys.repository.SysDictRepository
import jakarta.annotation.PreDestroy
import org.lionsoul.ip2region.xdb.Searcher
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

// Spring 容器销毁事件
@Component
class IP2RegionService(
    private val sysDictRepository: SysDictRepository
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(IP2RegionService::class.java)

    private var searcher: Searcher? = null

    private var unsafeInit: Boolean = false

    // 部分缓存, 线程不安全
    fun notThreadSafeInit(dbPath: String) {
        try {
            val vIndex = Searcher.loadVectorIndexFromFile(dbPath)
            val searcher = Searcher.newWithVectorIndex(dbPath, vIndex)
            this.unsafeInit = true
            this.searcher = searcher
        } catch (e: Exception) {
            System.out.printf("failed to load vector index from `%s`: %s\n", dbPath, e)
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
        val dictDetailView = this.sysDictRepository.findByCode("ip2region")
        val filePath = dictDetailView?.dictItems?.find { it.code == "filePath" }?.data
        if (!filePath.isNullOrEmpty()) {
            defaultInit(filePath)
            this.unsafeInit = false
            return
        }
        log.warn("未配置 ip2region.xdb 存储库")
    }

    fun search(ip: String): String {
        val searcher = this.searcher ?: run {
            log.warn("ip2region.xdb 存储库未初始化")
            return ""
        }

        return try {
            if (this.unsafeInit) {
                synchronized(this) {
                    searcher.search(ip)
                }
            } else {
                searcher.search(ip)
            }
        } catch (e: Exception) {
            log.error("ip2region.xdb 存储库查询失败", e)
            return ""
        }
    }

    @PreDestroy
    fun destroy() {
        this.searcher?.close()
    }
}