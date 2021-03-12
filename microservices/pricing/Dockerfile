# First stage of the build (compile and build the application)
FROM maven:3.6-adoptopenjdk-14 AS MAVEN_BUILD

COPY . pricing
RUN cd pricing && mvn -Dmaven.test.skip=true clean package

# its Second stage (copy the application compiled and packed, just to be run)
FROM adoptopenjdk/openjdk14

ARG artifact_name=pricing-service-0.0.1-SNAPSHOT.jar
COPY --from=MAVEN_BUILD pricing/target/pricing-service-0.0.1-SNAPSHOT.jar /usr/local/pricing/

EXPOSE 8082
WORKDIR /usr/local/pricing/
CMD ["java", "-jar",  "pricing-service-0.0.1-SNAPSHOT.jar"]
