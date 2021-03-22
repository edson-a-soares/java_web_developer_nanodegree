# First stage of the build (compile and build the application)
FROM maven:3.6.3-openjdk-8 AS MAVEN_BUILD

COPY . ecommerce
RUN cd ecommerce && mvn -Dmaven.test.skip=true clean package

# its Second stage (copy the application compiled and packed, just to be run)
FROM tomcat:8.0.41-jre8
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=MAVEN_BUILD ecommerce/target/ecommerce-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
