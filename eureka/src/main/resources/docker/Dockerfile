FROM java:8

COPY *.jar /home/eureka.jar

ENTRYPOINT ["java","-jar","/home/eureka.jar","–-spring.profiles.active=dev"]
# docker build -t eureka -f ../src/main/resources/docker/Dockerfile .
# docker run -d -p 9010:9010 eureka --name="eureka"
