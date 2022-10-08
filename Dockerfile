FROM openjdk:8-jdk-alpine
ADD target/shop_api_v1-0.0.1-SNAPSHOT.jar shop_api_v1-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shop_api_v1-0.0.1-SNAPSHOT.jar"]