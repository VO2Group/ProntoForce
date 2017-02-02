# Sample JAVA App with embedded server

## Quick start!

1. Ensure your JAVA_HOME is well defined and pointing to a java8+ JDK

2. Checkout the project and run the following command to start the server :

    - on windows :

        `mvnw.cmd spring-boot:run`

    - on linux/mac :

        `./mvnw spring-boot:run`

    - from your IDE :
    run or debug [JavaTestApplication](./src/main/java/com/vo2/JavaTestApplication.java) (class annotated with @SpringBootApplication)
 
3. Checking all is working :
    Visit 
    [localhost:8080](http://localhost:8080/)
    for web sample page or 
    [/rest/hellovo2](http://localhost:8080/rest/hellovo2) for REST API
     
    
## In depth..

A default embedded server (embedded tomcat) and an in memory relational database (hsql) are used with the default profile.
There are 2 mains controllers/entry points, a service component and some domain's staff. 
- _REST Controller_ : com.vo2.javatest.mvc.controllers.SampleRestController
    - It exposes "sample" resource containing just "id" (key) and "message" properties
    - You can use [Postman](https://www.getpostman.com/docs/) to quick test REST API
    - To list all available "samples" :
        
        `GET http://localhost:8080/rest/samples`
        
        {see [SampleRestController#allMessages](./src/main/java/com/vo2/javatest/mvc/controllers/SampleRestController.java#L39) method}

    - To access a single sample by its id
        
        `GET http://localhost:8080/rest/sample/1`
        
        {see   [SampleRestController#byIdMessage](./src/main/java/com/vo2/javatest/mvc/controllers/SampleRestController.java#L52) method}

    - To list all samples which message contains a substring
         
        `GET http://localhost:8080/rest/sample/like/mess` (will list all available samples with message that contains "mess")
        
        {see [SampleRestController#likeMessage](./src/main/java/com/vo2/javatest/mvc/controllers/SampleRestController.java#L128) method}

    - Ta add a new sample

        `POST http://localhost:8080/rest/sample`
        and with json as body :
        ```javascript
        {
            "message": "text of message here"
        }
        ```

        which will return the sample created and a http status 200 on success or a bad request status 403 otherwise
        ```javascript
        {
            "id": 4,
            "message": "text of message here"
        }
        ```

        {see [SampleRestController#addSample](./src/main/java/com/vo2/javatest/mvc/controllers/SampleRestController.java#L66) method}

    - To update an existing sample

        `PUT http://localhost:8080/rest/sample/1` (with same body as when adding a new sample)

        {see [SampleRestController#updateSample](./src/main/java/com/vo2/javatest/mvc/controllers/SampleRestController.java#L85) method}

    - To delete an entry by id

        `DELETE http://localhost:8080/rest/sample/3` (delete sample with id 3)

        {see [SampleRestController#deleteSample](./src/main/java/com/vo2/javatest/mvc/controllers/SampleRestController.java#L109) method}

- _Web Controller_ : com.vo2.javatest.mvc.controllers.SampleWebController
    There is only one method on root "/" using a thymeleaf template (src/main/resources/templates/sample.html)
    
- _Service Layer_ : packaged in com.vo2.javatest.services.SampleService which calls the data layer and convert returned database entities into disconnected DTOs
- _Domain / DATA Layer_ : packaged in com.vo2.javatest.domain => JPA entities definition, DTOs and low level data components (DAO/Spring DATA Repositories)

## Database "versioning" and migration tool

The project use [Liquibase](http://www.liquibase.org/) to create database and initialize data in samples table.

All changes are listed in changelog yaml [file](./src/main/resources/db/changelog/db.changelog-master.yaml).

## How to test?

JUnit is used for UT (\*\*/\*\*Test.java) or IT integration tests (\*\*/\*\*IT.java)

- For a simple unit test for a service class, please refer to [SampleServiceTest](./src/test/java/com/vo2/javatest/services/SampleServiceTest.java)
- There is an _**integration test**_ with embedded server running on a random port in [JavaTestOnRandomPortIT](./src/test/java/com/vo2/javatest/integration/JavaTestOnRandomPortIT.java)
- There is a _**mocked integration test**_ without starting a real servlet context but a mocked one and with a mock behaviour for the service in [JavaTestOnMockRESTServerIT](./src/test/java/com/vo2/javatest/JavaTestOnMockRESTServerIT.java)
- Finally, to test spring data repository with a real call to JPA behind a fresh in memory database, please look at [SampleRepositoryTest](./src/test/java/com/vo2/javatest/domain/repositories/SampleRepositoryTest.java)

To run unit tests and simple integration tests :

   `mvnw clean verify`

To skip integration tests :

   `mvnw clean verify -DskipITs`

To run only integration tests :

   `mvnw clean verify -DskipTests`


## Package and run as docker container for integration tests

Docker tests (satisfying pattern \*\*/\*\*DockerIT.java as [SampleDockerIT.java](./src/test/java/com/vo2/javatest/integration/docker/SampleDockerIT.java)) are integration tests that are skipped by default.

On a Docker host, you may start them by running :

   `mvnw clean verify -Docker`

The project uses [fabric8io/docker-maven-plugin](https://dmp.fabric8.io/) maven plugin which loads a [Dockerfile](./src/main/docker/Dockerfile). The pom.xml [section](./pom.xml#L136) add other details (port number,
maven lifecycle binding, ...)

There are 2 containers : the web application and Postgresql db. After tests run, all containers are stopped.

## Running as _prod_ profile

  `mvnw spring-boot:run -Drun.profiles=prod`

The [application-prod.properties](./src/main/resources/application-prod.properties) will override and extend the default [application.properties](./src/main/resources/application.properties)

This profile require a postgres database running on 5432 port. This requirement may be achieved with docker [postgres image](https://hub.docker.com/_/postgres/) :

  `docker pull postgres`

  `docker run --name postgres-javatest-prod -e POSTGRES_PASSWORD=passw0rd postgres`


## Where is the documentation?

The Spring Boot project is well documented [here](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/),
the Spring DATA JPA sub project is [here](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/). Visit this link for complete documentation about
[testing](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html) with Spring Boot
