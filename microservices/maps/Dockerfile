# First stage of the build (compile and build the application)
FROM maven:3.6-adoptopenjdk-14 AS MAVEN_BUILD

COPY . maps
RUN cd maps && mvn -Dmaven.test.skip=true clean package

# its Second stage (copy the application compiled and packed, just to be run)
FROM adoptopenjdk/openjdk14

ARG artifact_name=boogle-maps-0.0.1-SNAPSHOT.jar
COPY --from=MAVEN_BUILD maps/target/boogle-maps-0.0.1-SNAPSHOT.jar /usr/local/maps/

EXPOSE 9191
WORKDIR /usr/local/maps/
CMD ["java", "-jar",  "boogle-maps-0.0.1-SNAPSHOT.jar"]
