FROM openjdk:11-jre-slim
WORKDIR /app

ARG PORT=8080
ARG JAR_FILE
ARG TAG_VERSION
ARG EUREKA_SERVER=eurekaserver
ARG KEYCLOAK_SERVER=keycloak18
ARG KEYCLOAK_PORT=8080

ENV TAG_VERSION=${TAG_VERSION}
ENV PORT=${PORT}

COPY target/${JAR_FILE} /app/api.jar
EXPOSE ${PORT}
CMD ["java", "-Duser.language=pt", "-Duser.country=BR", "-Dspring.profiles.active=prod", "-jar", "api.jar"]
