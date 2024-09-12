import java.time.LocalDate

plugins {
    jacoco
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    id("pmd")
    id("com.diffplug.spotless") version "latest.release"
}

group = "com.zhengchalei.cloud.platform"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

kotlin { sourceSets.main { kotlin.srcDir("build/generated/ksp/main/kotlin") } }

repositories { mavenCentral() }

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

val jimmerVersion: String by project

dependencies {
    // database
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")

    // coffee
    implementation("com.github.ben-manes.caffeine:caffeine")

    implementation("org.springframework.boot:spring-boot-starter-quartz")

    // orm
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:$jimmerVersion")
    ksp("org.babyfish.jimmer:jimmer-ksp:$jimmerVersion")

    // ip2region
    implementation("org.lionsoul:ip2region:latest.release")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    // jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // jwt
    implementation("com.nimbusds:nimbus-jose-jwt:latest.release")

    // openapi
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:latest.release")
    // configuration processor
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // dev
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
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
        ktfmt().kotlinlangStyle()
        licenseHeader(
            """
            /*
             * 版权所有 © ${LocalDate.now().year} 郑查磊.
             * 保留所有权利.
             *
             * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
             */
        """
                .trimIndent()
        )
        target("src/main/kotlin/**/*.kt")
    }
    kotlinGradle {
        kotlin
        ktfmt().kotlinlangStyle()
        target("build.gradle.kts")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
}
