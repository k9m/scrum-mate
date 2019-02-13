FROM openjdk:8-jre-slim
VOLUME /tmp
COPY ./build/libs/scrum8-services-1.0-SNAPSHOT.jar /tmp/app.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/tmp/app.jar"]
EXPOSE 8082
