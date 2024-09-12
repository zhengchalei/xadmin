package com.zhengchalei.cloud.platform.config.jimmer

import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration
import org.babyfish.jimmer.meta.ImmutableProp
import org.babyfish.jimmer.meta.ImmutableType
import org.babyfish.jimmer.sql.cache.Cache
import org.babyfish.jimmer.sql.cache.caffeine.CaffeineValueBinder
import org.babyfish.jimmer.sql.cache.chain.ChainCacheBuilder
import org.babyfish.jimmer.sql.kt.cache.AbstractKCacheFactory
import org.babyfish.jimmer.sql.kt.cache.KCacheFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JimmerCacheConfiguration {

    val caffeineDuration: Duration = Duration.ofSeconds(60)

    @Bean
    fun cacheFactory(objectMapper: ObjectMapper): KCacheFactory {
        return object : AbstractKCacheFactory() {
            override fun createAssociatedIdCache(prop: ImmutableProp): Cache<*, *>? {
                return createCache(prop)
            }

            override fun createAssociatedIdListCache(prop: ImmutableProp): Cache<*, List<*>>? {
                return createCacheList(prop)
            }

            override fun createObjectCache(type: ImmutableType): Cache<*, *>? {
                return createCache(type)
            }

            override fun createResolverCache(prop: ImmutableProp): Cache<*, *>? {
                return createCache(prop)
            }

            private fun createCache(prop: ImmutableProp): Cache<*, *>? {
                return ChainCacheBuilder<Any, Any>()
                    .add(
                        CaffeineValueBinder.forProp<Any, Any>(prop)
                            .maximumSize(1024)
                            .duration(caffeineDuration)
                            .build()
                    )
                    .build()
            }

            private fun createCache(type: ImmutableType): Cache<*, *>? {
                return ChainCacheBuilder<Any, Any>()
                    .add(
                        CaffeineValueBinder.forObject<Any, Any>(type)
                            .maximumSize(1024)
                            .duration(caffeineDuration)
                            .build()
                    )
                    .build()
            }

            private fun createCacheList(prop: ImmutableProp): Cache<*, List<*>>? {
                return ChainCacheBuilder<Any, List<*>>()
                    .add(
                        CaffeineValueBinder.forProp<Any, List<*>>(prop)
                            .maximumSize(1024)
                            .duration(caffeineDuration)
                            .build()
                    )
                    .build()
            }
        }
    }
}
