## eCommerce with Security and DevOps

Udacity [Java Web Developer Nanodegree](https://www.udacity.com/course/java-developer-nanodegree--nd035) 
**fifth course** project, 
[eCommerce Application](https://github.com/udacity/nd035-c4-Security-and-DevOps).

****
#### Running with Docker

To go straight to the **application**, use Docker.

- ```docker-compose up -d```

Available addresses.
- [e-Commerce API](http://localhost:8080)
- [Jenkins](http://localhost:9000/blue)   
  **Disclaimer**: You should trigger one build in order to deploy the application at least once before trying to access it.
  It's all set on Jenkins. Just trigger the build and **approve** the deployment.
    - **Username**: ecommerce
    - **Password**: udacity

#### Useful links ###
* [eCommerce Udacity's skeleton repository.](https://github.com/udacity/nd035-c4-Security-and-DevOps)
* [Jenkins Configuration as Code with Docker.](https://www.digitalocean.com/community/tutorials/how-to-automate-jenkins-setup-with-docker-and-jenkins-configuration-as-code)
* [Jenkins Job Configuration automation.](https://www.digitalocean.com/community/tutorials/how-to-automate-jenkins-job-configuration-using-job-dsl)
* [Docker Splunk logging driver.](https://medium.com/@caysever/docker-splunk-logging-driver-c70dd78ad56a)
