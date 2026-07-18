plugins {
    id("java")
    // 1. Plugins oficiales de Spring Boot y gestión de dependencias instalados
    id("org.springframework.boot") version "3.5.13"
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

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${extra["springCloudVersion"]}")
    }
}

dependencies {
    // Bloque Spring Web, Discovery y Actuator heredando versiones automáticamente
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //-- Registro y Descubrimiento con Consul
    implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
    implementation("org.springframework.cloud:spring-cloud-starter-consul-config")

    //-- Metricas Prometheus
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    //-- OpenTelemetry Tracing
    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")

    runtimeOnly("org.postgresql:postgresql")
}


tasks.test {
    useJUnitPlatform()
}
