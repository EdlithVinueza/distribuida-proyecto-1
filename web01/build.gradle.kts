plugins {
    id("java")
    id("war")
    id("application")

}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
    //implementation("org.apache.commons:commons-collections4:4.5.0")
}

tasks.test {
    useJUnitPlatform()
}