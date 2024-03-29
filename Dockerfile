FROM gradle:7.6.1-jdk17-alpine AS TEMP_BUILD_IMAGE

ENV APP_HOME=/root/dev/
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME

# Copy all the files
COPY ./build.gradle ./gradlew ./gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY ./src $APP_HOME/src/

# Build desirable JAR
RUN ./gradlew clean build -x test


FROM eclipse-temurin:17-jdk-alpine
WORKDIR /root/

COPY --from=TEMP_BUILD_IMAGE /root/dev/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]