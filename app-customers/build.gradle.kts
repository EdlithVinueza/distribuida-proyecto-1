plugins {
    id("java")
    // 1. Plugins oficiales de Spring Boot y gestión de dependencias instalados
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "9.2.0"
}

group = "com.progAvanzada"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configuración moderna de Java 25
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

// Declaración correcta de la propiedad extra para Spring Cloud
extra["springCloudVersion"] = "2025.0.2"

dependencies {
    // Bloque Spring Web, Discovery y Actuator heredando versiones automáticamente
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.test {
    useJUnitPlatform()
}
