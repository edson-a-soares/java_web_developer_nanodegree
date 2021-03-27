FROM tomcat:8.0.41-jre8
COPY infrastructure/tomcat/tomcat-users.xml $CATALINA_HOME/conf/tomcat-users.xml

EXPOSE 8080
CMD ["catalina.sh", "run"]
