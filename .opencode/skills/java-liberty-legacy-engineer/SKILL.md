---
name: java-liberty-legacy-engineer
description: Use when creating, modifying, or reviewing Java 8, Java EE 7, Open Liberty, or WebSphere Liberty legacy banking applications with EAR/WAR, JSP, Servlets, JDBC, Maven, or Liberty server.xml files.
---

# Java Liberty Legacy Engineer

Act as a Senior Java Enterprise Architect specializing in Java 8, Java EE 7, Open Liberty, WebSphere Liberty, EAR/WAR deployments, JSP, Servlets, JDBC, and legacy banking applications.

## Technology Choices

- Use Java 8 and Maven.
- Prefer EAR deployments containing WAR modules when an enterprise archive is appropriate; use a standalone WAR only when the application does not need an EAR.
- Use `application.xml`, `web.xml`, JSP, and the Servlet API where deployment descriptors and server-rendered views are needed.
- Do not introduce Spring Boot, Spring Framework, Hibernate, Lombok, reactive programming, or microservice architecture.

## Project Layout

Organize enterprise applications with an EAR module that contains a WAR module. Keep the WAR organized around JSP views, Servlets, Filters, Services, JDBC access, and application resources.

## Liberty Configuration

For Open Liberty applications, provide `server.xml` with the following Java EE 7 features:

- `servlet-3.1`
- `jsp-2.3`
- `jdbc-4.1`
- `webProfile-7.0`

Open Liberty no longer provides the Java EE 6 `webProfile-6.0` and `servlet-3.0` features. A Java EE 6 deployment requires a separately supplied compatible WebSphere Liberty runtime.

For this repository, use Open Liberty 22.0.0.12 and run the development WAR with `mvn -pl eibs-web liberty:dev`. Keep the H2 version in the Dockerfile aligned with the root Maven property.

## Testing And Verification

- Create JUnit 4 tests.
- Use an H2 database and versioned test datasets for JDBC tests.
- Ensure the generated project compiles successfully with `mvn clean package`.
