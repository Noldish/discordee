FROM openjdk:17
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.jar

ENV JAVA_TOOL_OPTIONS "-Dcom.sun.management.jmxremote \
 -Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.port=5000 \
 -Dcom.sun.management.jmxremote.rmi.port=5000 \
 -Djava.rmi.server.hostname=0.0.0.0"
EXPOSE 5000

ENTRYPOINT ["java","-jar","/app.jar"]