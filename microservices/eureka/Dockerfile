# First stage of the build (compile and build the application)
FROM maven:3.6-adoptopenjdk-14 AS MAVEN_BUILD

COPY . eureka
RUN cd eureka && mvn -Dmaven.test.skip=true clean package

# its Second stage (copy the application compiled and packed, just to be run)
FROM adoptopenjdk/openjdk14

ARG artifact_name=eureka-0.0.1-SNAPSHOT.jar
COPY --from=MAVEN_BUILD eureka/target/eureka-0.0.1-SNAPSHOT.jar /usr/local/eureka/

EXPOSE 8761
WORKDIR /usr/local/eureka/
CMD ["java", "-jar",  "eureka-0.0.1-SNAPSHOT.jar"]
