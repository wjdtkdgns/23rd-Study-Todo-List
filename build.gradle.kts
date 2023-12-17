import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"

	/** jacoco **/
	id("jacoco")
}

group = "yapp.study"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

object DependencyVersion {
	const val SPRINGDOC_VERSION = "2.0.0"
	const val KOTEST_VERSION = "5.7.2"
	const val KOTEST_EXTENSION_VERSION = "1.1.2"
	const val MOCKK_VERSION = "1.13.8"
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${DependencyVersion.SPRINGDOC_VERSION}")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:${DependencyVersion.SPRINGDOC_VERSION}")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	runtimeOnly("com.mysql:mysql-connector-j")

	implementation("org.springframework.boot:spring-boot-starter-hateoas")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5:${DependencyVersion.KOTEST_VERSION}")
	testImplementation("io.kotest:kotest-assertions-core:${DependencyVersion.KOTEST_VERSION}")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:${DependencyVersion.KOTEST_EXTENSION_VERSION}")
	testImplementation("io.mockk:mockk:${DependencyVersion.MOCKK_VERSION}")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

noArg {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}


/** jacoco **/
tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	/** when finished test-all */
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		html.required.set(true)
		csv.required.set(false)
		xml.required.set(false)
	}

	classDirectories.setFrom(
		files(
			classDirectories.files.map {
				fileTree(it) {
					exclude(
						"**/*Application*",
						"**/*Config*",
						"**/*Dto*",
						"**/*Request*",
						"**/*Response*",
						"**/*Interceptor*",
						"**/*Exception*",
						"**/Q*.class"
					)
				}
			}
		)
	)

	finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			// https://docs.gradle.org/current/javadoc/org/gradle/testing/jacoco/tasks/rules/JacocoViolationRule.html#getElement--
			limit {
				minimum = 0.30.toBigDecimal()
			}
		}

		rule {
			enabled = true

			// https://docs.gradle.org/current/javadoc/org/gradle/testing/jacoco/tasks/rules/JacocoLimit.html#getCounter--
			limit {
				counter = "BRANCH"
				value = "COVEREDRATIO"
				minimum = 0.30.toBigDecimal()
			}

			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = 0.30.toBigDecimal()
			}
		}
	}
}