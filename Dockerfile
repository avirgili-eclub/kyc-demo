# Step 1: run tests and build über jar
FROM maven:3.8-eclipse-temurin-17-alpine as build

WORKDIR /build

# Cache Maven dependencies
COPY ./pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

# Build project
COPY ./src/ ./src/
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests


# Step 2: package über jar
FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="eliasmelgarejo@gmail.com"
LABEL org.opencontainers.image.source="https://github.com/eliasmelgarejo/vms-server.git"

# Set timezone
RUN apk add --no-cache alpine-conf && \
    setup-timezone -z America/Argentina/Buenos_Aires

# Create system user
RUN addgroup --system spring --gid 1000
RUN adduser --system spring --uid 1000 --ingroup spring
USER spring:spring

WORKDIR /home/spring
RUN mkdir resources_file

COPY --from=build --chown=spring:spring build/target/*.jar ./vms_server.jar

CMD ["java", "-jar", "vms_server.jar"]
