FROM eclipse-temurin:21
ARG JAR_FILE=target/*.jar
COPY ./target/duid-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]