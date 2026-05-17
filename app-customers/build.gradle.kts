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

// Configuración moderna de Java 25
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

dependencies {

    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.35.2"))

    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")


}

tasks.test {
    useJUnitPlatform()
}