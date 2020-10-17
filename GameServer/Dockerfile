FROM openjdk:8
ADD target/TypeWarsGameServer.jar TypeWarsGameServer.jar
ADD target/classes/words.txt words.txt
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "TypeWarsGameServer.jar"]