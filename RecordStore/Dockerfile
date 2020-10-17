FROM openjdk:8
ADD target/TypeWarsRecordStore.jar TypeWarsRecordStore.jar
ADD target/classes/db/migration db/migration/
EXPOSE 8082
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "TypeWarsRecordStore.jar"]