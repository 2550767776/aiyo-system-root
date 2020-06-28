FROM openjdk:8-jdk-alpine
ADD ../../../../target/eureka*.jar /home/eureka.jar

ENTRYPOINT ["java","-jar","/home/eureka.jar"]
