# stock-echoes-server

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/stock-echoes-server-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Extensions

To add an extension, you can write the command:

```shell script
./mvnw quarkus:add-extension -Dextensions='(extension name)'
```

| Dependency                  | Purpose                                                                                    | Use Case                                                                                |
|-----------------------------|--------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|
| **`hibernate-orm-panache`** | ORM framework to map Java objects to database tables and simplify DB access.               | Used for interacting with relational databases using Java objects (CRUD operations).    |
| **`jdbc-postgresql`**       | JDBC driver for PostgreSQL, allowing Java applications to connect to PostgreSQL databases. | Used to directly connect Java applications to PostgreSQL databases via JDBC.            |
| **`resteasy`**              | JAX-RS implementation to build RESTful web services in Java.                               | Used for creating REST APIs and handling HTTP requests and responses.                   |
| **`resteasy-jackson`**      | Integrates Jackson for JSON serialization/deserialization in RESTful services.             | Automatically converts Java objects to JSON and vice versa in RESTful APIs.             |
| **`lombok`**                | Reduces the necessity of writing boilerplate code.                                         | Automatically creates getters, setters and other methods when @Data is used on Entities |
| **`quarkus-resteasy-multipart`** | Enables receiving complex form-data in the body of requests                                |  |

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
