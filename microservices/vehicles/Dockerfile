# First stage of the build (compile and build the application)
FROM maven:3.6-adoptopenjdk-14 AS MAVEN_BUILD

COPY . vehiclesapi
RUN cd vehiclesapi && mvn -Dmaven.test.skip=true clean package

# its Second stage (copy the application compiled and packed, just to be run)
FROM adoptopenjdk/openjdk14

ARG artifact_name=vehicles-api-0.0.1-SNAPSHOT.jar
COPY --from=MAVEN_BUILD vehiclesapi/target/vehicles-api-0.0.1-SNAPSHOT.jar /usr/local/vehiclesapi/

EXPOSE 8080
WORKDIR /usr/local/vehiclesapi/
CMD ["java", "-jar",  "vehicles-api-0.0.1-SNAPSHOT.jar"]
