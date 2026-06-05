job "book-store" {
    datacenters = ["dc1"]

    group "servers" {

        count = 1

        network {
            port "http" {}
        }

        task "app-customers" {
            driver = "java"

            config {
                jar_path    = "c:/distribuida20262026/app-customers-0.0.1-SNAPSHOT.jar"
                jvm_options = ["-Xmx1024m", "-Xms128m"]
            }
            env{
                SERVER_PORT = "${NOMAD_PORT_http}"
            }
            service {
                provider = "nomad"
                name = "app-customers"
                port = "8090"
            }
        }
    }
}
