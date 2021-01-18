# First stage of the build (compile and build the application)
FROM maven:3.6-adoptopenjdk-14 AS MAVEN_BUILD

COPY . cloudstorage
RUN cd cloudstorage && mvn -Dmaven.test.skip=true clean package

# its Second stage (copy the application compiled and packed, just to be run)
FROM adoptopenjdk/openjdk14

ARG artifact_name=cloudstorage-0.1.jar
COPY --from=MAVEN_BUILD cloudstorage/target/cloudstorage-0.1.jar /usr/local/cloudstorage/

EXPOSE 8080
WORKDIR /usr/local/cloudstorage/
CMD ["java", "-jar",  "cloudstorage-0.1.jar"]
