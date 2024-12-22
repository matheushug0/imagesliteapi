FROM maven:3.8.7-openjdk-18-slim as builder
WORKDIR /app
COPY . .
RUN mvn dependency:resolve
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder ./app/target/*.jar ./application.jar
EXPOSE 8080

ENV POSTGRES_HOST=localhost
RUN echo "ENV POSTGRES_HOST=${POSTGRES_HOST}"

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "application.jar"]