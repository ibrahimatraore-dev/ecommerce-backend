FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /home/alten

COPY pom.xml .
COPY alten-Ecommerce-api/pom.xml alten-Ecommerce-api/pom.xml
COPY alten-Ecommerce-core/pom.xml alten-Ecommerce-core/pom.xml
COPY alten-Ecommerce-datasource/pom.xml alten-Ecommerce-datasource/pom.xml


RUN mvn dependency:go-offline -B

COPY docker .

RUN mvn clean package -DskipTests

RUN ls -a
RUN ls -a alten-Ecommerce-api/target

RUN cp alten-Ecommerce-api/target/alten-Ecommerce-api-0.0.1-SNAPSHOT.jar alten.jar


FROM eclipse-temurin:17-jre

WORKDIR /home

COPY --from=build /home/alten/alten.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/home/alten.jar"]
