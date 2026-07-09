# Plan de Estudio: Programación Distribuida (Nivel Código)

Este plan de estudio profundiza en **CÓMO** se programaron tus microservicios para lograr conectarse en este entorno distribuido. Analizaremos tu propio código fuente para entender la magia detrás de la arquitectura.

---

## 1. Conexión a la Base de Datos (PostgreSQL)

En un entorno distribuido, las credenciales y URLs de la base de datos no deben estar fijas en el código, sino que se inyectan como **Variables de Entorno** para que el contenedor sepa a dónde apuntar.

### En Quarkus (`authors-app` y `books-app`)
En tu archivo `application.properties` defines los valores por defecto locales:
```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/books20262026
quarkus.datasource.username=postgres
```
**¿Cómo se conecta en Docker?** En tu `docker-compose.yml`, sobreescribes esa URL inyectando la variable `QUARKUS_DATASOURCE_JDBC_URL: jdbc:postgresql://db-server:5432/books20262026`. Quarkus es inteligente y reemplaza automáticamente el `localhost` por `db-server`.

### En Spring Boot (`customers-app`)
En tu `application.yml` usas la estructura estándar de Spring:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/books20262026
```
Y en Docker inyectas `SPRING_DATASOURCE_URL: jdbc:postgresql://db-server:5432/books...` para conectarlo al contenedor real.

---

## 2. Registro y Descubrimiento de Servicios (Consul)

Aquí es donde tu proyecto se pone interesante, ya que utilizas 3 formas distintas de interactuar con Consul dependiendo del microservicio.

### A. Registro Programático (Vert.x) en `authors-app`
En `authors-app` tú programaste manualmente el registro usando el ciclo de vida de Quarkus. Observa tu clase `AuthorsLifecycle.java`:
```java
public void init(@Observes StartupEvent event, Vertx vertx) {
    // 1. Te conectas a Consul usando las variables inyectadas
    ConsulClientOptions options = new ConsulClientOptions()
            .setHost(consulHost).setPort(consulPort);
    this.client = ConsulClient.create(vertx, options);

    // 2. Le dices a Consul: "Búscame en esta IP y en la ruta /ping cada 10s"
    CheckOptions checkOptions = new CheckOptions().setHttp(urlCheck).setInterval("10s");

    // 3. Envías "etiquetas" (Tags) para que Traefik sepa enrutar el tráfico
    var tags = List.of(
            "traefik.enable=true",
            "traefik.http.routers.app-authors.rule=PathPrefix(`/app-authors`)"
    );

    // 4. Ejecutas el registro
    client.registerService(serviceOptions);
}
```
**Concepto a estudiar**: Aquí le estás inyectando configuración directamente a Traefik a través de Consul. Traefik lee esos *tags* y automáticamente crea la ruta `/app-authors`.

### B. Consumo mediante SmallRye Stork en `books-app`
`books-app` no solo se registra, sino que **necesita hablar con `authors`**. En lugar de usar la IP de `authors`, usa Stork (una librería de Quarkus para Service Discovery). Revisa tu `application.properties` en `books-app`:
```properties
quarkus.stork.authors-api.service-discovery.type=consul
quarkus.stork.authors-api.service-discovery.consul-host=${consul.host}
```
**Concepto a estudiar**: Gracias a esto, en el código Java de `books-app`, cuando quieras llamar a `authors`, tu cliente REST simplemente apuntará a `stork://authors-api`. Stork irá a Consul, preguntará "dame una IP válida para authors-api" y hará la petición. ¡Distribución pura!

### C. Registro Automático (Spring Cloud) en `customers-app`
Spring Boot hace esto "mágicamente" detrás de escena sin que escribas código Java. En tu `application.yml` le indicamos:
```yaml
  cloud:
    consul:
      host: ${SPRING_CLOUD_CONSUL_HOST:localhost}
      discovery:
        enabled: true
        register: true
        tags:
          - traefik.enable=true
          - traefik.http.routers.router-app-customers.rule=PathPrefix(`/app-customers`)
```
**Concepto a estudiar**: Spring Cloud Consul lee este YAML al arrancar, se conecta a Consul por su cuenta, y le pasa los mismos *tags* de Traefik que programaste a mano en `authors-app`. Dos caminos distintos (código manual vs configuración declarativa) para llegar al mismo resultado.

---

## 3. Exposición de Métricas (Prometheus)

Para que Prometheus pueda graficar en Grafana, cada microservicio debe exponer un *endpoint* con datos matemáticos. Tú lograste esto importando librerías específicas en cada proyecto.

### En Quarkus (`authors` y `books`)
Has importado la extensión `micrometer-registry-prometheus`. Quarkus automáticamente intercepta el uso de memoria, CPU y peticiones HTTP, y abre la ruta secreta:
`http://authors-app:8080/q/metrics`

### En Spring Boot (`customers`)
Agregaste Spring Boot Actuator. En tu `application.yml` habilitaste la ruta explícitamente:
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
```
Esto abre la ruta: `http://customers-app:8090/actuator/prometheus`

**Concepto a estudiar**: Fíjate cómo en el archivo `prometheus.yml` (en la carpeta `telemetria`) nosotros configuramos los *jobs* para ir a buscar estas rutas exactas cada 10 segundos (`scrape_interval`).

---

## 💡 Preguntas Guía para Estudiar

Si dominas las respuestas a estas preguntas mirando tu código, estarás listo para cualquier defensa:

1. **Si quiero cambiar la base de datos de PostgreSQL a MySQL**, ¿En qué archivos y variables debo hacer el cambio? *(Pista: application.properties y docker-compose)*
2. **Si escalo `authors-app` a 3 contenedores diferentes**, ¿Cómo sabe Traefik a cuál de los 3 enviar la petición del usuario? *(Pista: Consul actúa como un balanceador de carga pasivo).*
3. **¿Por qué `books-app` usa Stork en vez de hacer un `http://localhost:8070`?** *(Pista: Resiliencia. Si el puerto o la IP cambian, localhost falla. Stork siempre encuentra la ruta correcta preguntándole a Consul).*
