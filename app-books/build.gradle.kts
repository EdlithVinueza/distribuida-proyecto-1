plugins {
    id("java")
    id("io.quarkus") version "3.35.2"
    id("io.freefair.lombok") version "9.2.0"
}

group = "com.progAvanzada"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configuración de Java 21 (Recomendado para Quarkus 3.x)
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {

    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.35.2"))

    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    //--REST Client
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jsonb")

    //Discovery
    //implementation("io.quarkus:quarkus-smallrye-stork")
    //implementation("io.smallrye.stork:stork-service-discovery-static-list:2.6.3")



}

tasks.test {
    useJUnitPlatform()
}