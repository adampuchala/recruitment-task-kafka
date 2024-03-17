plugins {
    id("java")
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.adampuchala.springboot.kafka.poc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(project(mapOf("path" to ":samples-consumer")))
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:kafka:1.19.1")
    testImplementation("org.testcontainers:testcontainers:1.19.7")
    testImplementation("org.testcontainers:junit-jupiter:1.19.7")
    testImplementation("org.apache.kafka:kafka-clients:3.7.0")
    testImplementation("com.redis.testcontainers:testcontainers-redis:1.6.4")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

//kafkaAvroSerializer = "5.3.0"

tasks.test {
    useJUnitPlatform()
}
