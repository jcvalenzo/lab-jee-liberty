FROM maven:3.9.9-eclipse-temurin-8 AS builder
WORKDIR /workspace
COPY pom.xml ./
COPY eibs-web/pom.xml eibs-web/pom.xml
COPY eibs-ear/pom.xml eibs-ear/pom.xml
RUN mvn -B -q dependency:go-offline
COPY eibs-web eibs-web
COPY eibs-ear eibs-ear
RUN mvn -B clean package

FROM icr.io/appcafe/open-liberty:full-java8-openj9-ubi
COPY --chown=1001:0 eibs-web/src/main/liberty/config/server.xml /config/server.xml
COPY --chown=1001:0 --from=builder /workspace/eibs-ear/target/eibs-playground.ear /config/apps/eibs-playground.ear
COPY --chown=1001:0 --from=builder /root/.m2/repository/com/h2database/h2/2.1.214/h2-2.1.214.jar /config/lib/h2-2.1.214.jar
RUN configure.sh
