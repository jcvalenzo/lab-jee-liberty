# EIBS Playground

Laboratorio local de Java EE 6 para cuentas bancarias, empaquetado como EAR con un WAR. Usa Java 8, JSP, Servlets, JDBC, H2 y Open Liberty.

## Build y pruebas

```sh
mvn clean package
```

El EAR resultante es `eibs-ear/target/eibs-playground.ear`.

## Despliegue y entornos de desarrollo

Consulta [docs/despliegue.md](docs/despliegue.md) para ejecutar Liberty localmente, desplegar el EAR con Docker y preparar IntelliJ IDEA, Eclipse o Visual Studio Code.
