job "book-store" {
  datacenters = ["dc1"]

  group "servers" {

    count = 4

    network {
      port "http" {}
    }

    task "app-customers" {
      driver = "java"

      artifacts {
        source {
          source = "file:///C:/distribuida%202626/app-customers-0.0.1-SNAPSHOT.jar"
          destination = "local/"
          mode = "any"
        }
      }

      config {
        command = "java"
        #jar_path    = "c:/distribuida20262026/app-customers-0.0.1-SNAPSHOT.jar"
        jar_path    = "local/app-customers-0.0.1-SNAPSHOT.jar"
        jvm_options = ["-Xmx1024m", "-Xms128m"]
      }
      env {
        SERVER_PORT = "${NOMAD_PORT_http}"
        SPRING_CLOUD_CONSUL_HOST = "192.168.73.20"
      }

      service {
        provider = "nomad"
        name     = "app-customers"
        port     = "http"
      }
    }
  }
}