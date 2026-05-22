FROM gradle:8.8-jdk17 AS build
WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle

RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

RUN ./gradlew --no-daemon dependencies || true

COPY src src

RUN ./gradlew --no-daemon clean bootJar --stacktrace

FROM eclipse-temurin:17-jre
WORKDIR /app

ENV JAVA_OPTS=""
COPY --from=build /app/build/libs/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
