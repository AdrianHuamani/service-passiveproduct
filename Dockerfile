FROM openjdk:11
VOLUME /tmp
EXPOSE 9045
ADD ./target/passiveProduct-0.0.1-SNAPSHOT.jar ms-passiveproduct.jar
ENTRYPOINT ["java","-jar","/ms-passiveproduct.jar"]
