# Repository Notes

- This is a Maven reactor: `eibs-web` is the Java EE 7 WAR and `eibs-ear` packages it as `eibs-playground.ear`. Build and run the JUnit 4 suite from the root with `mvn clean package`.
- Compile source is Java 8 and the application APIs remain `javax.*`. Do not introduce Spring, Hibernate, Lombok, reactive APIs, microservices, or Jakarta EE APIs.
- The web module uses the Liberty JNDI datasource `jdbc/eibsDataSource`; its H2 schema and seed data are in `eibs-web/src/main/resources/db/` and are applied by `DatabaseInitializer` at application startup.
- Run the local Liberty development server with `mvn -pl eibs-web liberty:dev`; the development WAR uses `/eibs-web`, while the EAR deploys at `/eibs-playground`.
- Open Liberty uses `webProfile-7.0`, `servlet-3.1`, `jsp-2.3`, and `jdbc-4.1`; do not replace them with unavailable Java EE 6 features.
- `Dockerfile` builds the Maven EAR with Java 8 and deploys it to Open Liberty. Keep its H2 version aligned with the root Maven property.
