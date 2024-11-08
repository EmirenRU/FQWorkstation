# FQWorkstation

## Requirements

- Apache Maven
- Java 
- PostgreSQL v16
- JDK Development Kit 21

## Installation

1. PostgreSQL settings

```psql
$ CREATE USER "temp" WITH PASSWORD "temp" CREATEDB LOGIN;
$ CREATE DATABASE "info-system-department" "data-support" ;
```

2. To create a jar you need to

```bash
$ mvn install && mvn package
```

3. To execute instead

```bash
$ mvn spring-boot:run
```

## Docker

1. Create Package, if it's not created

```mvn
$ mvn package
```

2. Execute Makefile

```bash
$ make compose
```

## Project's structure 

- src/
    - main/
      - java/ru.emiren.infosystemdepartment
          - Config: Used for Spring Configuration (like adding another DB to the project or rewriting Spring Framework's properties)
          - Controller: Main dealers with providing pages to the clients or getting info from them, processing and sending to Services, if it's required.
          - DTO: (Data Transfer Object) is used when you don't want to disturb the DB with moving data from creating to processing.
          - Mapper: Model2DTO or DTO2Object. Simply converter.
          - Model: Spring uses it to create abstract tables into the DB.
          - Properties: Mappers with properties, if in the project possible to use one property many times it's better to create there than create each time.
          - Repository: Conversation between App and DB. Get me that, Create object in there or custom queries. (Object)Repository Limited.
          - Service: Required to not disturb controllers, if the controller will be disturbed with things that it does not need to do, better to place into Service. 
          - Util: Additional utilities to the project, by the start it's only for converting date.
          - InfoSystemDepartmentApplication.java: used for starting the Spring-Boot
      - resources
        - static: CSS/JS/IMG/ThirdParties(JS/CSS only)
        - templates: HTML with Thymleaf (Template Engine)
        - application.properties: Spring Configuration
        - data.sql: Starter Pack data.
        - logback-spring.xml: logs
        - template.docs: template for protocol generator
    - test: Unit Tests. (Still trying to learn)
- libs/: additional package that can't be downloaded through maven repository
- logs/: logs
- docker/: for creating docker image or sets of images

## Contributing 

- Emil Yusupov - Backend (Java, Spring Boot, PostgreSQL, JS, Ajax, Thymeleaf) 
<<<<<<< HEAD
- Ivan Podlesniy - Frontend (HTML, CSS, JS)
=======
- Ivan Podlesniy - Frontend (HTML, CSS, JS)
>>>>>>> dc23438d79718ab865be4a9dbb0fd74bc59e7d94
