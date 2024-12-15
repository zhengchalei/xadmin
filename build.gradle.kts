import java.util.*

plugins {
    jacoco
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id("pmd")
    id("com.diffplug.spotless") version "latest.release"
}

group = "com.zhengchalei.xadmin"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

kotlin { sourceSets.main { kotlin.srcDir("build/generated/ksp/main/kotlin") } }

repositories {
    mavenCentral()
    maven(url = "https://repo1.maven.org/maven2")
    maven(url = "https://maven.aliyun.com/repository/public")
}

dependencyManagement {
    imports {
        mavenBom("cn.hutool:hutool-bom:latest.release")
        mavenBom("net.dreamlu:mica-bom:latest.release")
    }
}

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

dependencies {
    // database
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")

    // coffee
    implementation("com.github.ben-manes.caffeine:caffeine")

    implementation("org.springframework.boot:spring-boot-starter-quartz")

    // orm
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:latest.release")
    ksp("org.babyfish.jimmer:jimmer-ksp:latest.release")

    // ip2region
    implementation("net.dreamlu:mica-core")
    implementation("net.dreamlu:mica-ip2region")
    implementation("net.dreamlu:mica-captcha")
    implementation("net.dreamlu:mica-redis")
    implementation("net.dreamlu:mica-xss")
    implementation("net.dreamlu:mica-logging")
    annotationProcessor("net.dreamlu:mica-auto")

    // hutool-json
    implementation("cn.hutool:hutool-json")

    // excel
    implementation("cn.idev.excel:fastexcel:latest.release")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")

    // mail
    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("org.springframework.boot:spring-boot-starter-aop")
    // jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // jwt
    implementation("com.nimbusds:nimbus-jose-jwt:latest.release")

    // redisson
    implementation("org.redisson:redisson-spring-boot-starter:latest.release")

    // openapi
    //    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    implementation("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:latest.release")

    // configuration processor
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // dev
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

pmd {
    isConsoleOutput = true
    toolVersion = "7.5.0"
    rulesMinimumPriority.set(5)
    threads = 4
    ruleSets(
        "category/kotlin/bestpractices.xml/FunctionNameTooShort",
        "category/kotlin/errorprone.xml/OverrideBothEqualsAndHashcode",
    )
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

jacoco {
    toolVersion = "0.8.12"
    reportsDirectory.set(layout.buildDirectory.dir("reports/jacoco"))
}

spotless {
    kotlin {
        ktfmt().kotlinlangStyle().configure {
            it.setBlockIndent(4)
            it.setContinuationIndent(4)
            it.setMaxWidth(120)
            it.setRemoveUnusedImports(true)
            it.setManageTrailingCommas(true)
        }
        licenseHeader(
            """
            /*
            Copyright ${Calendar.getInstance().get(Calendar.YEAR)} [郑查磊]
            
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
        """
                .trimIndent()
        )
        target("src/main/kotlin/**/*.kt")
    }
    kotlinGradle {
        ktfmt().kotlinlangStyle().configure {
            it.setBlockIndent(4)
            it.setContinuationIndent(4)
            it.setMaxWidth(120)
            it.setRemoveUnusedImports(true)
            it.setManageTrailingCommas(false)
        }
        target("build.gradle.kts")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
}
