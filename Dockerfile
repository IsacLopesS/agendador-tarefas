FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/agendador-tarefas.jar

EXPOSE 8081

CMD ["java","-jar", "/app/agendador-tarefas.jar"]