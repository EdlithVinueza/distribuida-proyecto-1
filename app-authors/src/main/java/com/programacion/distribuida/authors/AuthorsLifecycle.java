package com.programacion.distribuida.authors;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.List;

@ApplicationScoped
public class AuthorsLifecycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    // CORREGIDO: Se cambió "consul.posrt" por "consul.port" para coincidir con tu application.properties
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8070")
    Integer appPort;

    // Guardamos el cliente a nivel de clase para poder usarlo en el método destroy()
    private ConsulClient client;
    private String serviceId; //debe ser unico por intancias

    public void init(@Observes StartupEvent event, Vertx vertx) {
        System.out.println("authors-lifecycle: init");

        try {
            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            this.client = ConsulClient.create(vertx, options);

            String ipAddress = InetAddress.getLocalHost().getHostAddress();

            // CORREGIDO: Usamos %d ya que ambos parámetros son enteros (Integer)
            this.serviceId = "app-authors-%s:%d".formatted(appPort, appPort);

            var urlCheck = "http://%s:%d/ping".formatted(ipAddress, appPort);

            CheckOptions checkOptions = new CheckOptions()
                    .setHttp(urlCheck)
                    .setInterval("10s")
                    .setDeregisterAfter("10s");

            //metadatos para Traefik, para que pueda enrutar correctamente el tráfico a este servicio
            //pasamos las etiquetas necesarias para que Traefik pueda enrutar correctamente el tráfico a este servicio
            //las etiquetas venian priginalmente en app-static.yml de traefik
            var tags= List.of(
                    "traefik.enable=true",
                    "traefik.http.routers.app-authors.rule=PathPrefix(`/app-authors`)",
                    "traefik.http.routers.router-app-authors.middlewares=middleware-authors",
                    "traefik.http.middlewares.middleware-authors.stripprefix.prefixes=/app-authors"
            );

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-authors")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions)
                    .setTags(tags);

            client.registerService(serviceOptions)
                    .onSuccess(it -> System.out.println("Authors service registered in Consul with ID: " + serviceId));

            // CORREGIDO: Eliminados los tipos 'Void' y 'Throwable' de las lambdas para solucionar tus errores
            this.client.registerService(serviceOptions)
                    .onSuccess(it -> System.out.println("Authors service registered in Consul with ID: " + serviceId))
                    .onFailure(it -> {
                        System.out.println("Failed to register Authors service in Consul: " + it.getMessage());
                    });

        } catch (Exception e){
            e.printStackTrace();
        }
    }


        public void destroy(@Observes ShutdownEvent event, Vertx vertx) {
            System.out.println("authors-lifecycle: destroy");


                ConsulClientOptions options = new ConsulClientOptions()
                        .setHost(consulHost)
                        .setPort(consulPort);

                ConsulClient client = ConsulClient.create(vertx, options);

                client.deregisterService(serviceId)
                        .onSuccess(it -> System.out.println("Authors service deregistered from Consul with ID: " + serviceId))
                        .onFailure(it -> {
                            System.out.println("Failed to deregister Authors service from Consul: " + it.getMessage());
                        });

        }
}
