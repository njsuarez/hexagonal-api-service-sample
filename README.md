# Prices

API para la gestión de précios.

## Pre-requisitos

* OpenJDK 17
* Maven 3.2 (Opcional)

Se han incluido el wrapper de maven para facilitar la construcción y ejecución de la solución.

## Construccion

* Construcción de los binarios:

```bash
./mvnw clean compile
```

* Ejecución de test unitarios

```bash
./mvnw test
```

## Ejecución

Para ejecutar el servicio empleamos el comando siguiente

```bash
./mvnn spring-boot:run
```

El servicio estará ejecutandose en el puerto 8080.

## API docs

El servicio contiene documentación del api en la siguiente url: http://localhost:8080/api-docs.
Se puede encontrar la definición del API en el código `src/main/resources/prices-api.yaml`.

Las interfaces del API Rest se generan a partir de la definion del mismo que se encuentra en el archivo mencionado anteriormente.

Se ha optado por emplear todos los parametros como query params, pero otra opción podría haber sido emplear el brandId
como una header, lo cual tiene más sentido en una aproximación multimarca.

## Base de datos

La base de datos es persistida en memoria (H2) y se auto-rellena con los datos que se encuentra en el fichero `src/main/resources/database.yaml`.
Se ha dejado configurada la consola de h2 para hacer consultas, pero esta deshabilitada.

Para reactivar la consola es nesario descomentar la siguiente line en `src/main/resources/application.yaml`

```yaml
spring:
  h2:
#    console.enabled: true # Enabled database console on http://localhost:8080/h2-console, uncomment to use it
```

## Consulta

Podemos consultar el servicio desde la linea de comandos empleando curl.

```bash
curl --location 'localhost:8080/prices?productId=35455&brandId=1&date=2020-06-14T17%3A32%3A28Z'
```
