# Comunicación entre Microservicios (Direccionamiento Directo)

En esta fase del proyecto, la comunicación entre `app-books` (Cliente) y `app-authors` (Servidor) se realiza mediante **Direccionamiento Directo**, ya que aún no estamos utilizando **Service Discovery** (Consul/Eureka).

## 1. El Concepto
El direccionamiento directo significa que el servicio cliente conoce y tiene escrita en su código (o configuración) la IP y el Puerto exactos del servicio al que desea llamar.

## 2. Componentes del Cliente (`app-books`)

### A. La Interfaz (`AuthorRestClient`)
Define el **Contrato**. Indica qué rutas existen en el otro servicio y qué datos devuelven.
```java
public interface AuthorRestClient {
    @GET
    @Path("/find/{isbn}")
    List<AuthorDto> findByBook(@PathParam("isbn") String isbn);
}
```

### B. El Constructor (`RestClientBuilder`)
Es el encargado de instanciar el cliente "a mano" apuntando a una dirección física.
```java
AuthorRestClient client = RestClientBuilder.newBuilder()
    .baseUri(URI.create("http://127.0.0.1:8070")) // <--- IP y Puerto Directos
    .build(AuthorRestClient.class);
```

## 3. Flujo paso a paso de una petición

1. **Petición del Usuario**: El usuario llama a `GET /books/{isbn}` en `app-books`.
2. **Consulta Local**: `app-books` busca la información básica del libro en su propia base de datos.
3. **Llamada Externa**: `app-books` utiliza el `AuthorRestClient` para realizar una petición HTTP:
   - **URL**: `http://127.0.0.1:8070/authors/find/{isbn}`
4. **Procesamiento en Servidor**: `app-authors` recibe la petición en el puerto `8070`, consulta sus tablas (`authors` y `books_authors`) y devuelve un JSON con los autores.
5. **Deserialización**: El cliente en `app-books` recibe el JSON y lo convierte automáticamente en una lista de objetos `AuthorDto`.
6. **Respuesta Final**: `app-books` combina los datos del libro con los autores y entrega el resultado final al usuario.

## 4. Limitaciones (Por qué necesitaremos Service Discovery)
* **Rigidez**: Si el servicio de autores cambia de puerto, hay que modificar el código o la configuración del cliente.
* **Escalabilidad**: No permite repartir la carga (Load Balancing) de forma sencilla si tenemos múltiples instancias del servicio de autores.
* **Mantenimiento**: Es difícil gestionar muchas direcciones IP en sistemas con decenas de microservicios.
