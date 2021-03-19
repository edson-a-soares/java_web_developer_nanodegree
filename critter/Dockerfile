# First stage of the build (compile and build the application)
FROM maven:3.6-adoptopenjdk-14 AS MAVEN_BUILD

COPY . critter
RUN cd critter && mvn -Dmaven.test.skip=true clean package

# its Second stage (copy the application compiled and packed, just to be run)
FROM adoptopenjdk/openjdk14

ARG artifact_name=critter-0.0.1-SNAPSHOT.jar
COPY --from=MAVEN_BUILD critter/target/critter-0.0.1-SNAPSHOT.jar /usr/local/vehiclesapi/

EXPOSE 8082
WORKDIR /usr/local/critter/
CMD ["java", "-jar",  "critter-0.0.1-SNAPSHOT.jar"]
