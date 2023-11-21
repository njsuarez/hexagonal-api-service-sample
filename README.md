# API First approach with Hexagonal Architecture Sample

This is a sample of how to implement a API First service following and Hexagonal Architecture.

API is generated from OpenApi specification. (`../src/main/resources/prices-api.yaml`)

## Pre-requisites

* OpenJDK 17
* Maven 3.2 (Optional)

We include maven gradle to made easy build and run.

## Build

The build process will create the API interfaces to implement the service.

* Run build process:

```bash
./mvnw clean compile
```

* Run unit tests

```bash
./mvnw test
```

## Run

We can run service with default Spring Boot goal, it will expose the service in port 8080.

```bash
./mvnn spring-boot:run
```

## API documentation

The service is auto-documented and exposed it in the url: http://localhost:8080/api-docs.

We used query params to implement GET call but we can use some of then as headers if we try to use a multi-tenant approach

## Database

We use H2 in the sample to avoid to deploy a database service. 

The database is populated from a config file (`src/main/resources/database.yaml`) to have dataset of information to play with the service. 

We have disabled the H2 console but it is easy to enable it, only have to modify the application.yaml file. (`src/main/resources/application.yaml`)

```yaml
spring:
  h2:
#    console.enabled: true # Enabled database console on http://localhost:8080/h2-console, uncomment to use it
```

## Query sample

We can consume the service with this simple command or with postman.

```bash
curl --location 'localhost:8080/prices?productId=35455&brandId=1&date=2020-06-14T17%3A32%3A28Z'
```
