FROM jenkins/jenkins:lts

USER root
RUN apt-get update && apt-get install -y apt-transport-https \
       ca-certificates curl gnupg2 \
       software-properties-common

RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN apt-key fingerprint 0EBFCD88
RUN add-apt-repository \
       "deb [arch=amd64] https://download.docker.com/linux/debian \
       $(lsb_release -cs) stable"

RUN apt-get update && apt-get install -y docker-ce docker-ce-cli

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Install the needed plugins
COPY jenkins/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Setup Jenkins using Jenkins server using Jenkins Configuration as Code
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml
COPY jenkins/casc.yaml /var/jenkins_home/casc.yaml
