package com.programacion.distribuida.authors.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional; // Importante para el Optional de la imagen

@Path("/config")
public class TestConfig {

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer port;


    // 1. Eliminamos el campo 'private final String string' que causaba el error

    // 2. Constructor vacío para que CDI pueda instanciar la clase sin errores
    public TestConfig() {
    }

    @GET
    @Produces("text/plain")
    public String TEST() {

        // Punto de acceso a la configuración
        Config config = ConfigProvider.getConfig();

        // Imprimir fuentes de configuración (corrección con printf)
        config.getConfigSources().forEach(it -> {
            System.out.printf("[%d] \t %s%n", it.getOrdinal(), it.getName());
        });

        //-- RECUPERAR VALORES (Código extraído de la imagen)
        String url = config.getValue("quarkus.datasource.jdbc.url", String.class);
        Integer puerto = config.getValue("quarkus.http.port", Integer.class);
        Optional<String> title = config.getOptionalValue("app.title", String.class);

        System.out.println("-------------------------");
        System.out.println("URL: " + url);
        System.out.println("PUERTO: " + puerto);

        if (title.isPresent()) {
            System.out.println("TITLE: " + title.get());
        } else {
            System.out.println("TITLE: NO EXISTE");
        }

        System.out.println("Puerto (DI) port: " + port);
        return "OK";
    }
}
