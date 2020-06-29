FROM java:8

ADD *.jar /home/eureka.jar

ENTRYPOINT ["nohup","java","-jar","/home/eureka.jar","–-spring.profiles.active=dev","&"]
# docker build -t eureka -f ../src/main/resources/docker/Dockerfile .
# docker run --name="eureka" -d -p 9010:9010 eureka
