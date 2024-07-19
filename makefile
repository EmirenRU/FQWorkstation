ifeq ($(OS),Linux)

compose:
    cd docker && docker-compose up --build

else ifeq ($(OS),Windows_NT)
compose:
	cd docker ; docker-compose up --build
build:
	mvn package

else
compose:
    @echo "Not Supported OS"
endif

rundocker:
    docker run -t java-server:latest

all: compose rundocker

.PHONY: compose rundocker all
