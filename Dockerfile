FROM amazoncorretto:17
COPY build/libs/F1FS-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]