# Despliegue Y Entornos De Desarrollo

## Prerrequisitos

- JDK 8 instalado y seleccionado para compilar el proyecto. El código se compila con `source` y `target` 1.8.
- Maven 3.8 o superior disponible como `mvn`.
- Docker Desktop o un daemon Docker compatible para el despliegue en contenedor.
- Acceso a Maven Central y al registro de Open Liberty durante la primera descarga de dependencias, runtime o imagen.

Verifica las herramientas antes de empezar:

```sh
java -version
mvn --version
docker --version
```

`docker --version` solo es necesario para la opción de contenedor.

## Compilar El EAR

Desde la raíz del repositorio, ejecuta:

```sh
mvn clean package
```

El comando compila `eibs-web`, ejecuta los tests JUnit 4 contra H2 en memoria y genera el EAR en `eibs-ear/target/eibs-playground.ear`. Para ejecutar solo las pruebas del WAR:

```sh
mvn -pl eibs-web test
```

## Liberty Local

El flujo de desarrollo usa el WAR directamente. El plugin descarga Open Liberty, crea el servidor `eibsServer`, copia H2 y aplica `eibs-web/src/main/liberty/config/server.xml`.

```sh
mvn -pl eibs-web liberty:dev
```

Abre `http://localhost:9080/eibs-web/accounts`. Deten el proceso con `Ctrl+C`.

La configuración del servidor expone HTTP en `9080`, HTTPS en `9443` y el datasource JNDI `jdbc/eibsDataSource`. `DatabaseInitializer` aplica `db/schema.sql` y `db/data.sql` al arrancar, por lo que se crean dos cuentas de laboratorio si no existen.

Los datos H2 del servidor de desarrollo se guardan bajo:

```text
eibs-web/target/liberty/wlp/usr/servers/eibsServer/resources/
```

Para reiniciar el laboratorio, detén Liberty y elimina ese directorio. Esto borra todas las cuentas creadas localmente.

```sh
rm -rf eibs-web/target/liberty/wlp/usr/servers/eibsServer/resources
```

## EAR En Docker

El `Dockerfile` compila el reactor con Java 8, coloca `eibs-playground.ear` y el driver H2 en Open Liberty, y usa el mismo `server.xml`.

```sh
docker build -t eibs-playground .
docker run --rm -p 9080:9080 eibs-playground
```

Abre `http://localhost:9080/eibs-playground/accounts`. La URL cambia porque el EAR declara el contexto `/eibs-playground`; Liberty local usa el contexto del WAR `/eibs-web`.

El comando `--rm` elimina la base H2 al detener el contenedor. Para conservar datos entre ejecuciones, monta un volumen en `/config/resources`:

```sh
docker volume create eibs-h2-data
docker run --rm -p 9080:9080 -v eibs-h2-data:/config/resources eibs-playground
```

Para reiniciar los datos persistentes, elimina el volumen después de detener todos los contenedores que lo usen:

```sh
docker volume rm eibs-h2-data
```

## IntelliJ IDEA

1. Abre el directorio raíz o selecciona `pom.xml` y elige abrirlo como proyecto Maven.
2. En `File > Project Structure`, configura el Project SDK en JDK 8. En `Settings > Build Tools > Maven`, selecciona el mismo JDK para el importador y el runner de Maven.
3. En la ventana Maven, ejecuta `Lifecycle > clean` y después `Lifecycle > package`, o crea una configuración Maven con el comando `clean package` y el directorio de trabajo en la raíz.
4. Crea una configuración Maven para Liberty con los objetivos `-pl eibs-web liberty:dev` y el directorio de trabajo en la raíz. Iníciala y abre `http://localhost:9080/eibs-web/accounts`.
5. Ejecuta `AccountRepositoryTest` desde el editor o desde la ventana Maven. Debe ejecutarse con JUnit 4.

La integración visual de servidores de IntelliJ IDEA Ultimate es opcional. La configuración Maven anterior funciona también en IntelliJ IDEA Community.

## Eclipse

1. Usa una distribución Eclipse con soporte Java/Maven, por ejemplo Eclipse IDE for Enterprise Java and Web Developers.
2. Configura JDK 8 en `Window > Preferences > Java > Installed JREs` y marca ese JDK como predeterminado. Confirma el nivel de compilación 1.8 en `Java > Compiler`.
3. Importa el reactor con `File > Import > Maven > Existing Maven Projects` y selecciona el `pom.xml` raíz.
4. Ejecuta `Run As > Maven build...` con los objetivos `clean package` para compilar todo el reactor.
5. Crea otra ejecución Maven con `-pl eibs-web liberty:dev` para levantar Liberty y abre `http://localhost:9080/eibs-web/accounts`.
6. Ejecuta las clases de `eibs-web/src/test/java` como `JUnit Test`.

Liberty Tools para Eclipse puede instalarse desde Eclipse Marketplace para facilitar la exploración del servidor, pero el ciclo de build y ejecución del proyecto debe seguir usando Maven.

## Visual Studio Code

1. Instala las extensiones `Extension Pack for Java`, `Maven for Java` y `Liberty Tools` de IBM.
2. Abre la raíz del repositorio. Ejecuta `Java: Configure Java Runtime` desde la paleta de comandos y selecciona JDK 8 para el proyecto y para Maven.
3. En la vista Maven, ejecuta `clean` y `package` sobre el reactor raíz, o usa la terminal:

```sh
mvn clean package
```

4. Para iniciar Liberty, ejecuta en la terminal integrada:

```sh
mvn -pl eibs-web liberty:dev
```

5. Ejecuta o depura `AccountRepositoryTest` desde el panel Testing de Java. La aplicación estará disponible en `http://localhost:9080/eibs-web/accounts`.

Las extensiones ayudan a navegar el proyecto y ejecutar pruebas; la configuración de Liberty permanece en `eibs-web/src/main/liberty/config/server.xml` y no debe duplicarse en archivos del editor.

## Problemas Frecuentes

| Problema | Acción |
| --- | --- |
| El puerto 9080 está ocupado | Detén el proceso que lo usa o cambia `httpPort` en `eibs-web/src/main/liberty/config/server.xml`. |
| Maven usa un JDK incorrecto | Revisa `JAVA_HOME`, la configuración Maven del IDE y vuelve a comprobar `mvn --version`. |
| Liberty o dependencias no descargan | Comprueba conectividad a Maven Central; el primer inicio descarga el runtime y dependencias. |
| Se mantienen cuentas de una ejecución anterior | Detén Liberty y elimina su directorio `resources`, o elimina el volumen Docker `eibs-h2-data`. |
| La URL devuelve 404 | Usa `/eibs-web/accounts` para `liberty:dev` y `/eibs-playground/accounts` para el EAR en Docker. |
