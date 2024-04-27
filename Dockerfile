# Etapa de compilación
FROM maven:3.8.4 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package
COPY target/springboot-orq-challenge-0.0.1*.jar springboot-orq-challenge-0.0.1*.jar

# Ejecutar jdeps para analizar las dependencias
RUN jdeps --list-deps app.jar

# Etapa de empaquetado
FROM maven:3.8.4 AS packager
WORKDIR /app
COPY --from=build springboot-orq-challenge-0.0.1*.jar .
RUN jlink --add-modules java.base,java.logging --output jvm-runtime

# Etapa final
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=packager /app/jvm-runtime /opt/jvm-runtime
ENV PATH="$PATH:/opt/jvm-runtime/bin"
COPY --from=build springboot-orq-challenge-0.0.1*.jar .
CMD ["java", "-Xmx512m", "-XX:+UseG1GC", "-jar", "springboot-orq-challenge-0.0.1*.jar"]
