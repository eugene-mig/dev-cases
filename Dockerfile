FROM openjdk:14-jdk-alpine AS builder
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw verify clean --fail-never

COPY src src

RUN ./mvnw package -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:14-jdk-alpine
RUN addgroup -S cases && adduser -S cases -G cases
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
ENV JAVA_OPTS=/opt/config/
ENTRYPOINT ["java", "$JAVA_OPTS", "-cp","app:app/lib/*","com.trustpoint.cases.CasesApplication"]