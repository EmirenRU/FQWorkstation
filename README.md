# FQWorkstation

## Requirements

- Apache Maven
- Java 
- PostgreSQL v16

## Installation

1. PostgreSQL settings

```psql
$ CREATE USER "FQWorkstationUser" WITH PASSWORD "xxx" CREATEDB LOGIN;
$ CREATE DATABASE "info-system-department" "data-support";
```

2. To create a jar you need to

```bash
$ mvn install && mvn mvn package
```

3. To execute instead

```bash
$ java /target/app-VERSION.jar 
```


