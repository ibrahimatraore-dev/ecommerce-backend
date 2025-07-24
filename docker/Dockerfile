FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /home/dmp

COPY pom.xml .
COPY dmp-api/pom.xml dmp-api/pom.xml
COPY dmp-core/pom.xml dmp-core/pom.xml
COPY dmp-datasource/pom.xml dmp-datasource/pom.xml
COPY dmp-httpclient/pom.xml dmp-httpclient/pom.xml

RUN mvn dependency:go-offline -B

COPY . .

RUN mvn clean package -DskipTests

RUN ls -a

RUN ls -a dmp-api/target

RUN cp dmp-api/target/dmp-api-*.jar dmp.jar

FROM eclipse-temurin:17-jre

WORKDIR /home

COPY --from=build /home/dmp/dmp.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/home/dmp.jar"]
