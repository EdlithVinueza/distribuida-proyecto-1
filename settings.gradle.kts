plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "prog-distribuida"
include("web01")
include("web02")
include("app-authors")
include("app-books")
include("app-customers")
include("app-web")
