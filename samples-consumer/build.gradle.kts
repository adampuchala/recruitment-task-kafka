plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.adampuchala"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.kafka:spring-kafka")
	implementation(project(mapOf("path" to ":anomaly-storer")))
    implementation(project(mapOf("path" to ":domain")))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

allprojects {
	repositories {
		mavenCentral()
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
