FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]