# syntax=docker/dockerfile:1

FROM eclipse-temurin:11

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

COPY pictures/ pictures/

COPY src/ src/

CMD ["./mvnw", "spring-boot:run"]