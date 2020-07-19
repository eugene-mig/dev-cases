FROM maven:3.6.0-jdk-11-slim AS builder
WORKDIR /home/app
ADD src /home/app/src
ADD pom.xml /home/app
ADD .mvn /home/app/.mvn
ADD mvnw /home/app
ENV MAVEN_CONFIG ""
RUN ./mvnw package

FROM azul/zulu-openjdk-alpine:11.0.7
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=builder /home/app/target target
RUN JAR_FILE=target/*.jar
RUN ${JAR_FILE} app.jar
ENV JAVA_OPTS=/opt/config/
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]