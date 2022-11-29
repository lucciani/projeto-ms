FROM openjdk:11-jre-slim
WORKDIR /app

ARG PORT=8761
ARG JAR_FILE
ARG TAG_VERSION
ENV TAG_VERSION=${TAG_VERSION}
ENV PORT=${PORT}

COPY target/${JAR_FILE} /app/api.jar
EXPOSE ${PORT}
CMD ["java", "-Duser.language=pt", "-Duser.country=BR", "-jar", "api.jar"]
