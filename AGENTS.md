# Repository Notes

- This is a Maven reactor: `eibs-web` is the Java EE 6 WAR and `eibs-ear` packages it as `eibs-playground.ear`. Build and run the JUnit 4 suite from the root with `mvn clean package`.
- Compile source is Java 8. Do not introduce Spring, Hibernate, Lombok, reactive APIs, or microservices.
- The web module uses the Liberty JNDI datasource `jdbc/eibsDataSource`; its H2 schema and seed data are in `eibs-web/src/main/resources/db/` and are applied by `DatabaseInitializer` at application startup.
- Run the local Liberty development server with `mvn -pl eibs-web liberty:dev`; the development WAR uses `/eibs-web`, while the EAR deploys at `/eibs-playground`.
- `Dockerfile` builds the Maven EAR with Java 8 and deploys it to Open Liberty. Keep its H2 version aligned with the root Maven property.
