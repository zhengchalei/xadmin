plugins {
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	id("com.google.devtools.ksp") version "1.9.24-1.0.20"
	id ("tech.argonariod.gradle-plugin-jimmer") version "latest.release"
}

group = "com.zhengchalei.cloud.platform"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

kotlin {
	sourceSets.main {
		kotlin.srcDir("build/generated/ksp/main/kotlin")
	}
}

jimmer {
	version = "latest.release"
	dto {
		mutable = true
	}
}

repositories {
	mavenCentral()
}

val jimmerVersion: String by project

dependencies {


	// database
	runtimeOnly("com.mysql:mysql-connector-j")
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("com.h2database:h2")

	// orm
	implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:$jimmerVersion")
	ksp("org.babyfish.jimmer:jimmer-ksp:latest.release")

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

	// kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	//dev
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	systemProperty("spring.profiles.active", "test")
}