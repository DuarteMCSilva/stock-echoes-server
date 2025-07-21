# stock-echoes-server
````mermaid
classDiagram
    class Portfolio {
        - id: Long
        - name: String
        - created?: Date
    }

    class Ticker {
        - symbol: String
        - company_name: String
    }

    class Holdings {
        - symbol: String
        - name: String
        - quantity: int
        - avgCost: BigDecimal
    }

    class Transaction {
        - id: Long
        - date: Date
        - quantity: int
        - price: BigDecimal
    }

    %% Relationships

    Portfolio "1" --> "*" Holdings : contains
    Transaction "1" --> "1" Portfolio
    Transaction "1" --> "1" Ticker
````

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
