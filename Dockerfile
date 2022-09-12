FROM openjdk:17-oracle
ADD target/university-api.jar university-api.jar
ENTRYPOINT ["java", "-jar","university-api.jar"]
EXPOSE 8080