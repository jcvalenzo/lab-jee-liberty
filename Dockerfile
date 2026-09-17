FROM gradle:8.5-jdk17-alpine AS builder
WORKDIR /app
COPY . .

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/jee-liberty-*.jar app.jar
CMD ["java", "-jar", "app.jar"]