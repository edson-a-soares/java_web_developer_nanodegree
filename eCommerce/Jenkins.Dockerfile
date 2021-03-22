FROM jenkins/jenkins:alpine

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Install the needed plugins
COPY jenkins/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Setup Jenkins using Jenkins server using Jenkins Configuration as Code
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml
COPY jenkins/casc.yaml /var/jenkins_home/casc.yaml
