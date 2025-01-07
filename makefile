ifeq ($(OS),Linux)

compose:
	cd docker && docker-compose up --build
build:
	mvn package
start-nginx:
	nginx -c /config/nginx.conf

else ifeq ($(OS),Windows_NT)

compose:
	cd docker ; docker-compose up --build
build:
	mvn package
start-nginx:
	nginx.exe -c /config/nginx.conf

else

compose:
	@echo "Not Supported OS"

endif

rundocker:
	docker run -t java-server:latest

test-load:
	mvn gatling:test

all: compose rundocker

.PHONY: compose rundocker all
