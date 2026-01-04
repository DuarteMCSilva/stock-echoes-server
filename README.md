# Stock Echoes Server

## 1. How to run locally:

### Development
```shell script
  ./mvnw quarkus:dev -DskipTests -Ddebug
```

### Build image locally
```shell script
./mvnw -DskipTests clean package
```

```shell script
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/stock-echoes-server-jvm .
```

```shell script
docker run -i --rm -p 8080:8080 quarkus/stock-echoes-server-jvm
```


## 2. Data Model

````mermaid
classDiagram
    class Account {
        - id: Long
        - customer: Customer
        - email: String
        - birth_date: String
    }
    
    class Customer {
        - id: Long
        - firstName: String
        - lastName: String
    }

    class Portfolio {
        - id: Long
        - user_id: Long
        - name: String
        - created: Date
    }

    class Ticker {
        - isin: String
        - symbol: String
        - company_name: String
    }

    class Transaction {
        - id: Long
        - portfolio_id: Long
        - date: Date
        - quantity: int
        - price: BigDecimal
    }

    %% Relationships

    Account "1" --> "*" Portfolio
    Account "1" --> "1" Customer
    Portfolio "1" --> "*" Transaction
    Transaction "*" -->"1" Ticker
````