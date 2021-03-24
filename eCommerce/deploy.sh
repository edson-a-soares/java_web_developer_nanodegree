#!/bin/bash

curl --upload-file target/ecommerce-0.0.1.war "http://udacity:ecommerce@172.20.0.2:8080/manager/text/deploy?path=/&update=true"


# curl --verbose --upload-file $APPLICATION_WAR_FILE "http://${TOMCAT_USER}:${TOMCAT_PASSWORD}@${TOMCAT_IP}:${TOMCAT_PORT}/manager/text/deploy?path=&update=true"