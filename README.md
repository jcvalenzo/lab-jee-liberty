# EIBS Playground

Laboratorio local de Java EE 7 para cuentas bancarias, empaquetado como EAR con un WAR. Usa Java 8, `javax.*`, JSP, Servlets, JDBC, H2 y Open Liberty 22.0.0.12.

## Build y pruebas

```sh
mvn clean package
```

El EAR resultante es `eibs-ear/target/eibs-playground.ear`.

## Desarrollo local

```sh
mvn -pl eibs-web liberty:dev
```

El WAR se publica en `http://localhost:9080/eibs-web/accounts`. Liberty usa `webProfile-7.0`, Servlet 3.1, JSP 2.3 y JDBC 4.1, con el datasource `jdbc/eibsDataSource` respaldado por H2.

## Contenedor

```sh
docker build -t eibs-playground .
docker run --rm -p 9080:9080 eibs-playground
```

El EAR se publica en `http://localhost:9080/eibs-playground/accounts`.

## Despliegue y entornos de desarrollo

Consulta [docs/despliegue.md](docs/despliegue.md) para ejecutar Liberty localmente, desplegar el EAR con Docker y preparar IntelliJ IDEA, Eclipse o Visual Studio Code.
