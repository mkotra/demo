# How to run 

## Prerequisites
* java 17 installed 
* MongoDB 4.4.x running on standard port, without authorization check
* Docker can be used to run mongo:

    `$ docker pull mongo`

    `$ docker run -d -p 27017:27017 mongo`

* Docker is also required to perform full clean install with integration tests (Testcontainers library used)

## Start spring boot application

# Option 1
    $ ./mvnw spring-boot:run
# Option 2
    $ ./mvnw clean package
    $ java -jar target/demo-0.0.1-SNAPSHOT.jar
# Option 3
Just import it to IDE and run DemoApplication

## Navigate to swagger-ui
http://localhost:8080

/transactions/persist-random can be used to create random transactions. 
    