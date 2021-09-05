FROM gradle:jdk8-hotspot AS build
COPY --chown=gradle:gradle . /gradle/src
WORKDIR /gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim

EXPOSE 8189

RUN mkdir /app

COPY --from=build /gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar","/app/spring-boot-application.jar"]
