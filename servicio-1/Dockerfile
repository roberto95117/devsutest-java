FROM amazoncorretto:17.0.10
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/demo-0.0.1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]