# Commands

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

### Update Swagger-ui static file for Github Pages
```shell script
curl -H "Accept: application/json; charset=utf-8" http://localhost:8080/q/openapi?format=json -o swagger.json
```