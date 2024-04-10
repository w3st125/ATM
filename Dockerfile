FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
