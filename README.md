# Quarkus :pencil2: REST API

## About Quarkus and this example

[Quarkus](https://quarkus.io/) is a Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM.

It provides a fast boot time, low RSS memory and offers near instant scale up and high density memory utilization in container orchestration platforms like Kubernetes.

In this example you can check how to create an REST API in a Quarkus application.

A REST API is an application programming interface that follows the REST architectural style and allows for interaction with RESTful web services. REST stands for representational state transfer.

## Technical requirements

- [Maven](https://maven.apache.org/), for project management
- A [JDK](https://www.oracle.com/java/technologies/downloads). This is example is built on version 18

## How to set up the project

First we need to create a new project and add the [pom.xml](pom.xml) with the required dependencies and plugins
  - quarkus-resteasy - _to import Rest Easy API implementation_
  - quarkus-resteasy-jackson - _for the API mapping of the objects (can be quarkus-resteasy-json too)_
  - quarkus-maven-plugin - _The Maven's plugin for Quarkus compilation_
  - quarkus-junit5 - _The Quarkus Junit library for testing_
  - rest-assured - _For API testing_
  - maven-surefire-plugin - _a Maven's plugin to run the tests upon packaging_

## How to create the REST API

To create a REST API we simply need to annotate a class with @Path and add the path where the REST API will be mapped.

````java
@Path("/api/users")
````
Then it is just a matter of adding your REST API methods as we did with [UsersResource](src/main/java/com/codewithhades/quarkus/restapi/UsersResource.java).

REST API methods inherit the base REST API path and can optionally concat their own path as well as use path variables. They also support request body parameter, content type headers, validations and many more.

````java
@Path("/{id}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getUser(@PathParam("id") String id) {...}
````
Add also the [application.properties](src/main/resources/application.properties) to specify a context path such as _/app_

We will also add a [UsersResourceTest](src/test/java/com/codewithhades/quarkus/restapi/UsersResourceTest.java) in order to check that the REST API behaves as expected.

## How to run the Quarkus application

Use Maven to package the JAR (and run the test), and then run the JAR with Java from a console
````bash
mvn clean package  
java -jar target/quarkus-app/quarkus-run.jar
````
Once your Quarkus application is running you can operate the REST API by calling it from a terminal

````bash
# To add an users
curl -X POST localhost:8080/app/api/users -H "Content-Type: application/json" -d '{"name": "Anakin","surname":"Skywalker"}'
# To list all users
curl localhost:8080/app/api/users
# To list an user by ID (which you can find by listing all)
curl localhost:8080/app/api/users/8a53a7da-ec0c-4d68-ab0a-d12e16013348
# To update an user by ID (which you can find by listing all)
curl -X PUT localhost:8080/app/api/users/8a53a7da-ec0c-4d68-ab0a-d12e16013348 -H "Content-Type: application/json" -d '{"name": "Darth","surname":"Vader"}'
# To delete an user by ID (which you can find by listing all)
curl -X DELETE localhost:8080/app/api/users/8a53a7da-ec0c-4d68-ab0a-d12e16013348
````
I hope you found this example useful!

:coffee: May Java be with you!