FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/userPurchase.xsd userPurchase.xsd
COPY src/main/resources/init.sql init.sql
ENTRYPOINT ["java","-jar","/app.jar"]
