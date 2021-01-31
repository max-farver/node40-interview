FROM gradle:jdk15 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

FROM openjdk:15

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/example/*.jar /app/interview-app.jar
COPY hello-world.yml /app/hello-world.yml

CMD ["java","-jar","/app/interview-app.jar","server","/app/hello-world.yml"]