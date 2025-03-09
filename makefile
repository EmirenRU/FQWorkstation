ifeq ($(OS),Linux)

compose:
	cd docker && docker-compose up --build
build:
	mvn package
start-nginx:
	nginx -c /config/nginx.conf
docker:
	mvn clean package
	mkdir -p docker-build/docker docker-build/fqw docker-build/support docker-build/protocol docker-build/frontend
	cp fqw/target/*.jar docker-build/fqw/target/app.jar
	cp support/target/*.jar docker-build/support/target/app.jar
	cp protocol/target/*.jar docker-build/protocol/target/app.jar
	rm -rf ./frontend/node_modules ./frontend/dist
	cp -r frontend/* docker-build/frontend/
	7z a -mx=9 -m0=lzma2 docker-build.7z ./docker-build
7z:
	7z a -mx=9 -m0=lzma2 docker-build.7z ./docker-build/

else ifeq ($(OS),Windows_NT)

react-install:
	cd frontend ; npm install
react:
	cd frontend ; npm run dev
compose:
	cd docker ; docker-compose up --build
build:
	mvn package
start-nginx:
	nginx.exe -c /config/nginx.conf
docker:
	mvn clean package
	mkdir -p docker-build/docker docker-build/fqw docker-build/support docker-build/protocol docker-build/frontend
	cp fqw/target/*.jar docker-build/fqw/target/app.jar
	cp support/target/*.jar docker-build/support/target/app.jar
	cp protocol/target/*.jar docker-build/protocol/target/app.jar
	rm -rf ./frontend/node_modules ./frontend/dist
	cp -r frontend/* docker-build/frontend/
	7z a -mx=9 -m0=lzma2 docker-build.7z ./docker-build
7z:
	7z a -mx=9 -m0=lzma2 docker-build.7z ./docker-build/
else

compose:
	@echo "Not Supported OS"

endif

.PHONY: docker docker-build
rundocker:
	docker run -t java-server:latest

test-load:
	mvn gatling:test

all: compose rundocker

.PHONY: compose rundocker all
