# FQWorkstation

## Branches

- Monolith: (Thymeleaf and HTML templates). Needs some correcting to match up to the features from microservices branch
- Microservices: React + Spring (REST) and modules' separation

## Requirements
### The traditional way
- Apache Maven
- Java 
- PostgreSQL v16
- JDK Development Kit 21

### By using Docker

- Docker

## Installation
### The traditional Way
1. Install requirements from https://github.com/EmirenRU/FQWorkstation/releases/tag/installer-windows

2. To create a jar, you need to

```bash
$ mvn install && mvn package
```
or 
```bash
$ mvn clean install && mvn package
```

3. Either double click on jars or
#### Windows
``` bash
$ ./launcher.ps1 start
```
#### Linux
``` bash
$ ./launcher.sh start
```
4. Run React application
```bash
$ cd frontend ; npm run dev (Windows)
$ cd frontent && npm run dev (Linux)
```

## Docker

### Using Makefile

```bash
$ make compose
```

### By traditional CMD or Bash

```
$ cd docker && docker-compose up --build (Linux)
$ cd docker ; docker-compose up --build (Windows)
```
### By using PowerShell
- Proceed to the Docker folder
- To start the application run "docker-start.ps1" as PowerShell script
- To remove the application from Docker run "docker-remove.ps1" as PowerShell script

## Project's structure 

- src/
    - main/
      - java/ru.emiren.infosystemdepartment
          - Config: Used for Spring Configuration (like adding another DB to the project or rewriting Spring Framework's properties)
          - Controller: Main dealers with providing pages to the clients or getting info from them, processing and sending to Services if it's required.
          - DTO: (Data Transfer Object) is used when you don't want to disturb the DB with moving data from creating to processing.
          - Mapper: Model2DTO or DTO2Object. Simply converter.
          - Model: Spring uses it to create abstract tables into the DB.
          - Properties: Mappers with properties, if in the project possible to use one property many times, it's better to create there than create each time.
          - Repository: Conversation between App and DB. Get me that, Create an object in there or custom queries. (Object)Repository Limited.
          - Service: Required to not disturb controllers, if the controller is disturbed with things that it does not need to do, better to place into Service. 
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
- ps1/: PowerShell scripts for Windows Installer (needs to automation the process)
- db/: scripts for deploying PostgreSQL in docker
- docker-build/: folder to create the docker build installer and archive it to .7z

## Module Structure

- fqw: The Core. It keeps the data of FQW and do SQL transactions. 
- protocol: Processing the files (Decrees and Tables) and generates protocol (get data from FQW and sends data to keep in the system)
- support: The module for supporting customers' tickets (not finished: hub, processing, connecting to "email") 
- email: The module allows processing the incoming messages to the client. 
- auth: The module for authentication (α version)


## Frontend Structure
- f/
    - src/
      - api/
          - getData.tsx: Used for Lecturers table and selectors.
          - downloadApi.tsx: used for downloading template files.
          - queryClient.tsx: used for form validation on support page.
          - SelectorData.json: fake selector data for test purposes.
          - data.json: fake table data for test purposes.
          - send.tsx: used for sending messages on support page.
      - assets/ (Images that are used globally)
      -  button/
          - button.tsx: Used for support form.
      - FAQ/
          - faq.css: stylesheet for manual page.
          - FAQ.tsx: contains the page logic and structure.
              - images/: screenshots used for manual page.
      - FormField/
          - FormField.tsx: used as building block for support form.
      - Functions/
          - ProtocolDocs/
              - hash.tsx: used for file hash generation and sending files.
              - sendFile.css: stylesheet for page.
              - sendFile.tsx: contains logic and structure for page .
                (the rest files are the image resources for the exact specific page)
          - ProtocolForm/
              - FormPages/
                  - ComissionData.tsx: contains logic and structure for Comission data page.
                  - FQWdata.tsx: contains logic and structure for FQW data page.
                  - ProtocolData.tsx: contains logic and structure for Protocol data.
                  - ReviewerData.tsx: contains logic and structure for Reviewer data page.
                  - StudentData.tsx: contains logic and structure for Student data page.
                  - TeachersData.tsx: contains logic and structure for Teacheers data page.
              - FormWrapper/
                  - FormWrapper.tsx: wrapper for multistep form.
              - FormHook/
                  - FormWrapper.tsx: hook which allows page changing functionality in the form .
              - protocol.css: stylesheet for page.
              - protocol.tsx: contains logic and structure for the entire multistep form
            ( To be continued if Ivan Podlesniy remains alive)
## Contributing 

- Emil Yusupov - Backend (Java, Spring Boot, PostgreSQL, JS, Ajax, Thymeleaf) 
- Ivan Podlesniy - Frontend (HTML, CSS, JS, Ajax, select2, jquery, xtermjs)
