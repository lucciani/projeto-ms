FROM openjdk:11-jre-slim
WORKDIR /app

ARG JAR_FILE
ARG TAG_VERSION
ARG EUREKA_SERVER=eurekaserver
ENV TAG_VERSION=${TAG_VERSION}

COPY target/${JAR_FILE} /app/api.jar
CMD ["java", "-Duser.language=pt", "-Duser.country=BR", "-Dspring.profiles.active=prod", "-jar", "api.jar"]
