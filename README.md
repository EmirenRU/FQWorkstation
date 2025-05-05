# FQWorkstation

## Manual

- [Backend](manual/backend/index.md)
- [Frontend](manual/frontend/index.md)

## Branches

- Monolith: (Thymeleaf and HTML templates). Needs some correcting to match up to the features from microservices branch
- Microservices: React + Spring (REST) and modules' separation

## Requirements

### Traditional Installation
- [Apache Maven](https://maven.apache.org/download.cgi)
- Java
- [PostgreSQL v17](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
- [JDK Development Kit 21](https://adoptium.net/temurin/releases/)

### Installation Using Docker
- [Docker](https://www.docker.com)

## Installation

### Traditional Installation
1. Install the required components from [this link](https://github.com/EmirenRU/FQWorkstation/releases/tag/installer-windows).

2. To create a JAR file, run one of the following commands:
   ```bash
   $ mvn install && mvn package
   ```
   or 
   ```bash
   $ mvn clean install && mvn package
   ```

3. To run the application, either double-click on the JAR files or use the following commands:
   - **Windows:**
     ```bash
     $ ./launcher.ps1 start
     ```
   - **Linux:**
     ```bash
     $ ./launcher.sh start
     ```

4. To run the React application, use the following commands:
   - **Windows:**
     ```bash
     $ cd frontend && npm run dev
     ```
   - **Linux:**
     ```bash
     $ cd frontend && npm run dev
     ```

### Installation Steps for Docker

1. **Download and Extract the Archive**
   - Download the archive from the following link and extract it:
     [Download FQWorkstation](https://github.com/EmirenRU/FQWorkstation/archive/refs/heads/microservices.zip)

2. **Install Windows Subsystem for Linux (WSL)**
   - a) Activate the script `activate-wsl.ps1` located in the `ps1` folder to install WSL, or enter the following command in the terminal:
     ```bash
     wsl --install
     ```
   - b) If the above steps do not work, enable the following options in "Windows Features":
     - "Virtual Machine Platform"
     - "Windows Subsystem for Linux"

3. **Install Docker**
   - Download and install Docker from the official website: [Docker Official Site](https://www.docker.com/)

4. **Start the Docker Application**
   - Navigate to the Docker folder and ensure that Docker is running. Then, execute the following script to start the application:
     ```powershell
     .\docker\docker-start.ps1
     ```

5. **Remove the Docker Container (if needed)**
   - To remove the container, run the following script:
     ```powershell
     .\docker\docker-remove.ps1
     ```

### Installation Using Docker

#### Using Makefile
```bash
$ make compose
```

#### Using Traditional CMD or Bash
- **Linux:**
  ```bash
  $ cd docker && docker-compose up --build
  ```
- **Windows:**
  ```bash
  $ cd docker && docker-compose up --build
  ```

#### Using PowerShell
1. Navigate to the Docker folder.
2. To start the application, run the following PowerShell script:
   ```powershell
   .\docker\docker-start.ps1
   ```
3. To remove the application from Docker, run:
   ```powershell
   .\docker\docker-remove.ps1
   ```

This structure provides a comprehensive guide for both traditional and Docker-based installations, ensuring clarity and ease of use.
   
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
- frontend/
    - src/
      - api/
          - getData.tsx: Used for getting/processing data for Lecturers table and selectors.
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
              - MultiStepHook/
                  - MultiStepHook.tsx: hook which allows page changing functionality in the form .
              - protocol.css: stylesheet for page.
              - protocol.tsx: contains logic and structure for the entire multistep form.
          - Layout/
              - header.tsx: header component.
              - footer.tsx: footer component.
              - header.css: header component stylesheet.
              - footer.tsx: footer component stylesheet.
         - Lecturers/
              - lecturers.tsx: base component which calls search form and table display component from SearchForm folder.
         - SearchForm/
              - display.tsx: component which connects form and table.
              - form.tsx: component for displaying search form.
              - load.tsx: component for displaying form.
              - save.tsx: used for saving selected options in local storage.
              - form.css: form stylesheet.
              - display.css: table stylesheet.
         - Support/
              - checked.svg: resource for form checkbox.
              - support.tsx: form component.
              - support.css: form stylesheet.
         - App.tsx: launches layout and  displays page.
         - App.css: root css styles for entire page.
         - content.tsx: context provider for form and table -- essential root component.
         - main.tsx: launches App.tsx -- essential root component.
            ( To be continued if Ivan Podlesniy remains alive)
     
## Contributing 

- Emil Yusupov - Backend (Java, Spring Boot, PostgreSQL, JS, Ajax, Thymeleaf) 
- Ivan Podlesniy - Frontend (HTML, CSS, JS, Ajax, select2, jquery, xtermjs)
