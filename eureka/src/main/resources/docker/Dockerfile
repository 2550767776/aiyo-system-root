FROM java:8
WORKDIR ../../../../
ADD ../../../../target/eureka*.jar /home/eureka.jar

ENTRYPOINT ["java","-jar","–spring.profiles.active=dev","/home/eureka.jar"]
