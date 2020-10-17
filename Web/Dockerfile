FROM openjdk:8
ADD target/TypeWarsWeb.war TypeWarsWeb.war
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "TypeWarsWeb.war"]